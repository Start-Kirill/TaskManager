package by.it_academy.user_service.endpoints.web.support.converters;

import by.it_academy.user_service.core.dto.UserDetailsImpl;
import by.it_academy.user_service.dao.entity.User;
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

        pairs.add(new ConvertiblePair(User.class, UserDetailsImpl.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == UserDetailsImpl.class) {
            return source;
        }


        User user = (User) source;

        UserDetailsImpl userDetails = new UserDetailsImpl();

        userDetails.setUuid(user.getUuid());
        userDetails.setFio(user.getFio());
        userDetails.setUsername(user.getMail());
        userDetails.setRole(user.getRole());
        userDetails.setStatus(user.getStatus());
        userDetails.setPassword(user.getPassword());

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        userDetails.setAuthorities(authorities);

        return userDetails;


    }
}
