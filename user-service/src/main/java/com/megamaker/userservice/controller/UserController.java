package com.megamaker.userservice.controller;

import com.megamaker.userservice.dto.UserDto;
import com.megamaker.userservice.mapper.UserMapper;
import com.megamaker.userservice.service.UserService;
import com.megamaker.userservice.vo.Greeting;
import com.megamaker.userservice.vo.RequestUser;
import com.megamaker.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return "It's working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        //return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> saveUser(@RequestBody RequestUser requestUser) {
        UserDto userDto = UserMapper.INSTANCE.toUserDto(requestUser);

        userService.saveUser(userDto);

        ResponseUser responseUser = UserMapper.INSTANCE.toResponseUser(userDto);
        return ResponseEntity.created(URI.create("test")).body(responseUser);
    }
}
