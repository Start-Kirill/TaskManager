package by.it_academy.task_manager_common.support.spring.converters;

import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserDtoToUserDetailsConverter implements Converter<UserDto, UserDetailsImpl> {

    @Override
    public UserDetailsImpl convert(UserDto source) {
        UserDetailsImpl userDetails = new UserDetailsImpl();

        userDetails.setUuid(source.getUuid());
        userDetails.setFio(source.getFio());
        userDetails.setUsername(source.getMail());
        userDetails.setRole(source.getRole());
        userDetails.setStatus(source.getStatus());

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(source.getRole().getRoleName()));
        userDetails.setAuthorities(authorities);


        return userDetails;
    }
}
