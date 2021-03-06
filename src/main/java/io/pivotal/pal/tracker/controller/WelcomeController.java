package io.pivotal.pal.tracker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("home")
public class WelcomeController {

    @Value("${WELCOME_MESSAGE}")
    private String welcomeMessage;

    @GetMapping("/")
    public String sayHello() {
        return welcomeMessage;
    }
}