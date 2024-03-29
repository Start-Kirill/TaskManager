package by.it_academy.audit_service.endpoints.web.filters;

import by.it_academy.audit_service.service.api.IUserClientService;
import by.it_academy.audit_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isEmpty;


@Component
public class JwtFilter
        extends OncePerRequestFilter {

    private final JwtTokenHandler jwtTokenHandler;

    private final ConversionService conversionService;

    private final IUserClientService userService;


    public JwtFilter(@Qualifier(value = "userFeignClientService")IUserClientService userService, JwtTokenHandler jwtTokenHandler, ConversionService conversionService) {
        this.userService = userService;
        this.jwtTokenHandler = jwtTokenHandler;
        this.conversionService = conversionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1].trim();
        try {
            jwtTokenHandler.validate(token);
        } catch (CommonErrorException ex) {
            List<ErrorResponse> errors = ex.getErrors();
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(objectMapper.writeValueAsString(errors));
            response.setContentType("application/json");
            filterChain.doFilter(request, response);
            return;
        }


        UserDetailsImpl userDetails = null;
        if (UserRole.SYSTEM.toString().equals(jwtTokenHandler.getRole(token))) {
            userDetails = new UserDetailsImpl();
            userDetails.setRole(UserRole.SYSTEM);
            userDetails.setUsername(jwtTokenHandler.getMail(token));
            userDetails.setPassword("System");
            userDetails.setAccountNonLocked(true);
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(UserRole.SYSTEM.getRoleName()));
            userDetails.setAuthorities(authorities);
        } else {
            UserDto user = this.userService.get(header, UUID.fromString(jwtTokenHandler.getUuid(token)));
            userDetails = this.conversionService.convert(user, UserDetailsImpl.class);
        }

        if (!userDetails.isAccountNonLocked()) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
