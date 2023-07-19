package by.it_academy.user_service.endpoints.web.controllers;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserDto;
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
    public ResponseEntity<?> create(@RequestBody UserCreateDto dto){
        this.userService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> getAll() {
        this.userService.get();
        return new ArrayList<>();
    }


}
