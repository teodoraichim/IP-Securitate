package com.security;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
    @RequestMapping(value="/login",method=POST)
    public boolean login(@RequestParam(value="username") String name,@RequestParam(value="password") String pass) {
        User user=new User(String.format(template,name),String.format(template,pass),"");
        return user.login();
    }
//    @RequestMapping(value="/register",method=POST)
//    {
//    }
//
//    @RequestMapping(value="/activate",method=POST)
//


}