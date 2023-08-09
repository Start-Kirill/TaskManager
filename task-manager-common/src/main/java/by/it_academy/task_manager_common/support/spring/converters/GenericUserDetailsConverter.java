package by.it_academy.task_manager_common.support.spring.converters;

import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenericUserDetailsConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(UserDto.class, UserDetailsImpl.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == UserDetailsImpl.class) {
            return source;
        }

        UserDetailsImpl userDetails = new UserDetailsImpl();

        UserDto user = (UserDto) source;

        userDetails.setUuid(user.getUuid());
        userDetails.setFio(user.getFio());
        userDetails.setUsername(user.getMail());
        userDetails.setRole(user.getRole());
        userDetails.setStatus(user.getStatus());

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        userDetails.setAuthorities(authorities);


        return userDetails;
    }
}
