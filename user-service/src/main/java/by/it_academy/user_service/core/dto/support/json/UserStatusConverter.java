package by.it_academy.user_service.core.dto.support.json;

import by.it_academy.user_service.core.dto.exceptions.NotCorrectUserStatusException;
import by.it_academy.user_service.core.enums.UserStatus;
import com.fasterxml.jackson.databind.util.StdConverter;

public class UserStatusConverter extends StdConverter<String, UserStatus> {
    @Override
    public UserStatus convert(String status) {
        if (!UserStatus.ACTIVATED.getName().equals(status) &&
                !UserStatus.DEACTIVATED.getName().equals(status) &&
                !UserStatus.WAITING_ACTIVATION.getName().equals(status)) {
            throw new NotCorrectUserStatusException("User status is incorrect");
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
