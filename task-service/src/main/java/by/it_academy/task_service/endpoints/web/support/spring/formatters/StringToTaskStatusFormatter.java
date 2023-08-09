package by.it_academy.task_service.endpoints.web.support.spring.formatters;

import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.endpoints.web.exceptions.NotValidTaskStatusException;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

public class StringToTaskStatusFormatter implements Formatter<TaskStatus> {

    private static final String TASK_STATUS_FIELD_NAME = "status";

    @Override
    public TaskStatus parse(String text, Locale locale) throws ParseException {
        if (TaskStatus.IN_WORK.toString().equals(text)) {
            return TaskStatus.IN_WORK;
        } else if (TaskStatus.BLOCK.toString().equals(text)) {
            return TaskStatus.BLOCK;
        } else if (TaskStatus.DONE.toString().equals(text)) {
            return TaskStatus.DONE;
        } else if (TaskStatus.WAIT.toString().equals(text)) {
            return TaskStatus.WAIT;
        } else if (TaskStatus.CLOSE.toString().equals(text)) {
            return TaskStatus.CLOSE;
        }
        throw new NotValidTaskStatusException(Map.of(TASK_STATUS_FIELD_NAME, "Passed status not valid or not exists"));
    }

    @Override
    public String print(TaskStatus object, Locale locale) {
        return object.toString();
    }
}
