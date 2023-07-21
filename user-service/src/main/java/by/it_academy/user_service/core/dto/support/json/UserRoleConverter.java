package by.it_academy.user_service.core.dto.support.json;

import by.it_academy.user_service.core.dto.exceptions.NotCorrectUserRoleException;
import by.it_academy.user_service.core.enums.UserRole;
import com.fasterxml.jackson.databind.util.StdConverter;

public class UserRoleConverter extends StdConverter<String, UserRole> {
    @Override
    public UserRole convert(String s) {
        if (!UserRole.USER.getName().equals(s) && !UserRole.ADMIN.getName().equals(s)) {
            throw new NotCorrectUserRoleException("User role is incorrect");
        }
        if (UserRole.USER.getName().equals(s)) {
            return UserRole.USER;
        } else {
            return UserRole.ADMIN;
        }
    }
}
