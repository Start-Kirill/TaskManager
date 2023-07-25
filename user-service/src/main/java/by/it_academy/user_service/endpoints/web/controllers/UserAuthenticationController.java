package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        this.userAuthenticationService.signIn(dto);
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


}
