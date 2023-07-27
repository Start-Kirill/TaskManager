package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserAuthenticationController {

    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }


    @PostMapping("/registration")
    public ResponseEntity<?> singIn(@RequestBody @Valid UserRegistrationDto dto) {
        this.userAuthenticationService.signIn(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //TODO
    @GetMapping(path = "/verification", params = {"code", "mail"})
    public ResponseEntity<?> verify(@RequestParam @NotBlank(message = "Code must not to be empty") @NotNull(message = "Code is missing") String code,
                                    @RequestParam @NotBlank(message = "Mail must not to be empty") @NotNull(message = "Mail is missing") String mail) {
        this.userAuthenticationService.verify(code, mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto dto) {
        this.userAuthenticationService.login(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
