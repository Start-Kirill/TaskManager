package by.it_academy.task_service.endpoints.web.filters;


import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_service.service.api.IUserClientService;
import by.it_academy.task_service.utils.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isEmpty;


@Component
public class JwtFilter
        extends OncePerRequestFilter {

    private final JwtTokenHandler jwtTokenHandler;

    private final ConversionService conversionService;

    private final IUserClientService userService;


    public JwtFilter(IUserClientService userService, JwtTokenHandler jwtTokenHandler, ConversionService conversionService) {
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
        jwtTokenHandler.validate(token);

        UserDto user = this.userService.get(header, UUID.fromString(jwtTokenHandler.getUuid(token)));
        UserDetailsImpl userDetails = this.conversionService.convert(user, UserDetailsImpl.class);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
