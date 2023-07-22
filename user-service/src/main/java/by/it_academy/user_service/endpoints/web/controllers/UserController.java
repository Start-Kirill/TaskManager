package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserDto;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import by.it_academy.user_service.core.utils.Utils;
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

    private static final String FIO_FIELD_NAME = "fio";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "status";

    private static final String UUID_PARAM_NAME = "uuid";

    private static final String DT_UPDATE_PARAM_NAME = "dt_update";

    private IUserService userService;

    private ConversionService conversionService;

    public UserController(IUserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateDto dto) {

        List<SpecificError> specificErrors = checkCorrectStructure(dto);

        if (!specificErrors.isEmpty()) {
            StructuredErrorResponse structuredErrorResponse = Utils.makeStructuredError(specificErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        }

        ResultOrError resultOrError = this.userService.save(dto);
        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getErrorResponses());
            }
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody UserCreateDto dto, @PathVariable UUID uuid, @PathVariable Long dt_update) {

        List<SpecificError> specificErrors = checkCorrectStructure(dto);

        if (!specificErrors.isEmpty()) {
            StructuredErrorResponse structuredErrorResponse = Utils.makeStructuredError(specificErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        }

        ResultOrError resultOrError = this.userService.update(dto, uuid, dt_update);
        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getErrorResponses());
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer size) {
        ResultOrError resultOrError = this.userService.get(page, size);

        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getErrorResponses());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultOrError.getUsers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUser(@PathVariable UUID uuid) {

        ResultOrError resultOrError = this.userService.get(uuid);

        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getErrorResponses());
            }
        }

        UserDto userDto = resultOrError.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    private List<SpecificError> checkCorrectStructure(UserCreateDto dto) {
        List<SpecificError> specificErrors = new ArrayList<>();
        if (dto.getMail() == null) {
            specificErrors.add(new SpecificError(MAIL_FIELD_NAME, "Field mail is required"));
        }
        if (dto.getFio() == null) {
            specificErrors.add(new SpecificError(FIO_FIELD_NAME, "Field fio is required"));
        }
        if (dto.getRole() == null) {
            specificErrors.add(new SpecificError(ROLE_FIELD_NAME, "Field role is required"));
        }
        if (dto.getStatus() == null) {
            specificErrors.add(new SpecificError(STATUS_FIELD_NAME, "Field status is required"));
        }
        if (dto.getPassword() == null) {
            specificErrors.add(new SpecificError(PASSWORD_FIELD_NAME, "Field password is required"));
        }
        return specificErrors;
    }


}
