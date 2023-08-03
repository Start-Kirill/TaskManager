package by.it_academy.user_service.endpoints.web.support.json;

import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.HashMap;
import java.util.Map;

public class UserStatusConverter extends StdConverter<String, UserStatus> {

    private static final String STATUS_FIELD_NAME = "status";

    @Override
    public UserStatus convert(String status) {
        if (!UserStatus.ACTIVATED.getName().equals(status) &&
                !UserStatus.DEACTIVATED.getName().equals(status) &&
                !UserStatus.WAITING_ACTIVATION.getName().equals(status)) {
            Map<String, String> errors = new HashMap<>();
            errors.put(STATUS_FIELD_NAME, "User status is incorrect");
            throw new NotValidUserBodyException(errors);
        }

        if (UserStatus.WAITING_ACTIVATION.getName().equals(status)) {
            return UserStatus.WAITING_ACTIVATION;
        } else if (UserStatus.ACTIVATED.getName().equals(status)) {
            return UserStatus.ACTIVATED;
        } else {
            return UserStatus.DEACTIVATED;
        }
    }
}
