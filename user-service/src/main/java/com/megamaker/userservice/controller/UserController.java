package com.megamaker.userservice.controller;

import com.megamaker.userservice.repository.UserEntity;
import com.megamaker.userservice.service.UserDto;
import com.megamaker.userservice.mapper.UserMapper;
import com.megamaker.userservice.service.UserService;
import com.megamaker.userservice.vo.Greeting;
import com.megamaker.userservice.vo.RequestUser;
import com.megamaker.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {
    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in User Service on PORT %s", env.getProperty("local.server.port"));
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

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(userEntity ->
                result.add(UserMapper.INSTANCE.toResponseUser(userEntity))
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser responseUser = UserMapper.INSTANCE.toResponseUser(userDto);

        return ResponseEntity.ok(responseUser);
    }
}
