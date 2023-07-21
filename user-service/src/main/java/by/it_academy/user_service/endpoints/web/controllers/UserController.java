package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserDto;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateDto dto) {

        if (!ifCorrectStructure(dto)) {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse(ErrorType.ERROR, "The request contains invalid data. Change the request and send it again"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
        }

        ResultOrError resultOrError = this.userService.save(dto);
        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getErrorResponses());
            }
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> getAll() {
        this.userService.get();
        return new ArrayList<>();
    }

    private boolean ifCorrectStructure(UserCreateDto dto) {
        if (dto.getMail() == null) {
            return false;
        }
        if (dto.getFio() == null) {
            return false;
        }
        if (dto.getRole() == null) {
            return false;
        }
        if (dto.getStatus() == null) {
            return false;
        }
        if (dto.getPassword() == null) {
            return false;
        }
        return true;
    }


}
