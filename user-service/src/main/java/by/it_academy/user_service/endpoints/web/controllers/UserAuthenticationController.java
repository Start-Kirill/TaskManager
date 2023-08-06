package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.task_manager_common.entity.User;
import by.it_academy.user_service.service.UserHolder;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import by.it_academy.user_service.utils.JwtTokenHandler;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserAuthenticationController {

    private final IUserAuthenticationService userAuthenticationService;

    private final JwtTokenHandler tokenHandler;

    private final UserHolder userHolder;

    private final ConversionService conversionService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService,
                                        JwtTokenHandler tokenHandler,
                                        UserHolder userHolder,
                                        ConversionService conversionService) {
        this.userAuthenticationService = userAuthenticationService;
        this.tokenHandler = tokenHandler;
        this.userHolder = userHolder;
        this.conversionService = conversionService;
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
        String token = this.userAuthenticationService.login(dto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

        return ResponseEntity.ok().headers(httpHeaders).build();
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        UserDetailsImpl user = this.userHolder.getUser();
        User me = this.userAuthenticationService.getMe(user.getUuid());
        UserDto userDto = this.conversionService.convert(me, UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/verification/{mail}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable String mail) {
        this.userAuthenticationService.sendCodeAgain(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
