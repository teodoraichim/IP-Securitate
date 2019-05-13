package com.security;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    //    private final AtomicLong counter = new AtomicLong();
//
    @RequestMapping(value="/login",method=POST)
    public String login(@RequestParam(value="username") String name,@RequestParam(value="password") String pass,@RequestHeader("Authorization") String auth) {
        User user=new User(name,pass,"");
        System.out.println("-"+name+"-"+pass+"-");
        if(user.login()) return user.createSession();
        return "false";
    }
    @RequestMapping(value="/register",method=POST)
    public boolean register(@RequestParam(value="username") String name,@RequestParam(value="password") String pass,@RequestParam(value="mail")String mail) {
        User user=new User(String.format(template,name),String.format(template,pass),"");
        try {
            return user.register();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
    @RequestMapping(value="/activate",method=POST)
    public boolean activate(@RequestParam(value="username") String name,@RequestParam(value="password") String pass,@RequestParam(value="code") String code) {
        User user=new User(String.format(template,name),String.format(template,pass),"");
        return user.activate(code);
    }


}
