package com.Hr.Project.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResController {
    @RequestMapping(value = "/getData")
    public String login(){
        return "login";
    }
}
