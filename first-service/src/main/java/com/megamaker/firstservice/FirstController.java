package com.megamaker.firstservice;

import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstController {
    Environment env;

    @Autowired
    public FirstController(Environment env) {
        this.env = env;
    }

    @GetMapping("/welcome")
    public String test() {
        return "Welcome to the First service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "First Service return";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());

        return String.format("First Service Custom filter on PORT %s"
                , env.getProperty("local.server.port"));
    }
}
