package by.it_academy.user_service.core.dto;

import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import by.it_academy.user_service.dao.entity.User;

import java.util.List;

public class ResultOrError {

    private User user;

    private List<User> users;

    private StructuredErrorResponse structuredErrorResponse;

    private List<ErrorResponse> errorResponses;

    public ResultOrError() {
    }

    public ResultOrError(User user, List<User> users, StructuredErrorResponse structuredErrorResponse, List<ErrorResponse> errorResponses) {
        this.user = user;
        this.users = users;
        this.structuredErrorResponse = structuredErrorResponse;
        this.errorResponses = errorResponses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public StructuredErrorResponse getStructuredErrorResponse() {
        return structuredErrorResponse;
    }

    public void setStructuredErrorResponse(StructuredErrorResponse structuredErrorResponse) {
        this.structuredErrorResponse = structuredErrorResponse;
    }

    public List<ErrorResponse> getErrorResponses() {
        return errorResponses;
    }

    public void setErrorResponses(List<ErrorResponse> errorResponses) {
        this.errorResponses = errorResponses;
    }

    public boolean hasError() {
        return structuredErrorResponse != null || errorResponses != null;
    }
}
