package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import by.it_academy.user_service.core.utils.Utils;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAuthenticationController {

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String FIO_FIELD_NAME = "fio";

    private static final String PASSWORD_FIELD_NAME = "password";

    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }


    @PostMapping("/registration")
    public ResponseEntity<?> singIn(@RequestBody UserRegistrationDto dto) {
        List<SpecificError> specificErrors = checkStructure(dto);
        if (!specificErrors.isEmpty()) {
            StructuredErrorResponse structuredErrorResponse = Utils.makeStructuredError(specificErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        }

        ResultOrError resultOrError = this.userAuthenticationService.signIn(dto);
        if (resultOrError.hasError()) {
            if (resultOrError.getStructuredErrorResponse() != null) {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getStructuredErrorResponse());
            } else {
                return ResponseEntity.status(resultOrError.getHttpStatus()).body(resultOrError.getErrorResponses());
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //TODO
    @GetMapping(path = "/verification", params = {"code", "mail"})
    public ResponseEntity<?> verify(@RequestParam String code, @RequestParam String mail) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<SpecificError> checkStructure(UserRegistrationDto dto) {
        List<SpecificError> specificErrors = new ArrayList<>();
        if (dto.getMail() == null) {
            specificErrors.add(new SpecificError(MAIL_FIELD_NAME, "Field mail is required"));
        }
        if (dto.getFio() == null) {
            specificErrors.add(new SpecificError(FIO_FIELD_NAME, "Field fio is required"));
        }
        if (dto.getPassword() == null) {
            specificErrors.add(new SpecificError(PASSWORD_FIELD_NAME, "Field password is required"));
        }
        return specificErrors;
    }


}
