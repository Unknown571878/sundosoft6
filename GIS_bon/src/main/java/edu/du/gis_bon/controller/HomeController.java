package edu.du.gis_bon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "main/home";
    }

    @GetMapping("/login")
    public String user() {
        return "user/login";
    }

    @GetMapping("/loginform")
    public String login() {
        return "user/loginform";
    }
}
