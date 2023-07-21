package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserDto;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    private ConversionService conversionService;

    public UserController(IUserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
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

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody UserCreateDto dto, @PathVariable UUID uuid, @PathVariable Long dt_update) {

        if (!ifCorrectStructure(dto)) {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse(ErrorType.ERROR, "The request contains invalid data. Change the request and send it again"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
        }

        ResultOrError resultOrError = this.userService.update(dto, uuid, dt_update);
        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getErrorResponses());
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer size) {
        ResultOrError resultOrError = this.userService.get(page, size);

        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getErrorResponses());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultOrError.getUsers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUser(@PathVariable UUID uuid) {

        ResultOrError resultOrError = this.userService.get(uuid);

        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultOrError.getErrorResponses());
            }
        }

        UserDto userDto = resultOrError.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
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
