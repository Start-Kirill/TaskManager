package by.it_academy.user_service.core.dto;

import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResultOrError {

    private UserDto user;

    private PageOfUsers users;

    private StructuredErrorResponse structuredErrorResponse;

    private List<ErrorResponse> errorResponses;

    private HttpStatus httpStatus;

    public ResultOrError() {
    }

    public ResultOrError(UserDto user, PageOfUsers users, StructuredErrorResponse structuredErrorResponse, List<ErrorResponse> errorResponses, HttpStatus httpStatus) {
        this.user = user;
        this.users = users;
        this.structuredErrorResponse = structuredErrorResponse;
        this.errorResponses = errorResponses;
        this.httpStatus = httpStatus;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public PageOfUsers getUsers() {
        return users;
    }

    public void setUsers(PageOfUsers users) {
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean hasError() {
        return structuredErrorResponse != null || errorResponses != null;
    }
}
