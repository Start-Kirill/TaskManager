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

    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }


    @PostMapping("/registration")
    public ResponseEntity<?> singIn(@RequestBody UserRegistrationDto dto) {
        this.userAuthenticationService.signIn(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(path = "/verification", params = {"code", "mail"})
    public ResponseEntity<?> verify(@RequestParam String code,
                                    @RequestParam String mail) {
        this.userAuthenticationService.verify(code, mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        this.userAuthenticationService.login(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/verification/{mail}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable String mail) {
        this.userAuthenticationService.sendCodeAgain(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
