package com.megamaker.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/second-service")
public class SecondController {
    @GetMapping("/welcome")
    public String test() {
        return "Welcome to the Second service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "Second Service return";
    }

    @GetMapping("/check")
    public String check() {
        return "Second Service Custom filter return";
    }
}
