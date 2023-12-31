package by.it_academy.user_service.endpoints.web.support.json;

import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.user_service.endpoints.web.exceptions.NotCorrectUserRoleException;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.HashMap;
import java.util.Map;

public class UserRoleConverter extends StdConverter<String, UserRole> {

    private static final String ROLE_FIELD_NAME = "role";

    @Override
    public UserRole convert(String s) {

        if (UserRole.USER.toString().equals(s)) {
            return UserRole.USER;
        } else if (UserRole.ADMIN.toString().equals(s)) {
            return UserRole.ADMIN;
        } else if (UserRole.MANAGER.toString().equals(s)) {
            return UserRole.MANAGER;
        } else if (UserRole.SYSTEM.toString().equals(s)) {
            return UserRole.SYSTEM;
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put(ROLE_FIELD_NAME, "User role is incorrect");
            throw new NotCorrectUserRoleException(errors);
        }
    }
}
