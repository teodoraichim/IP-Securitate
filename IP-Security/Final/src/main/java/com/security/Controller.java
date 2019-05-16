package com.security;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Controller {

    @RequestMapping(value="/login",method=POST)
    ResponseEntity<String> login(@RequestParam(value="username") String name, @RequestParam(value="password") String pass) {
        User user=new User(name,pass,"");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        if(user.login())
        {
            String json="{"+"\"success\":true,\"session_id\":\""+user.createSession()+"\"}";
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(json);

        }
        else
        {
            String json="{"+"\"success\":false,\"message\":\""+user.getLoginError()+"\"}";
            return ResponseEntity.badRequest().headers(headers).body(json);
        }
    }
    @RequestMapping(value="/register",method=POST)
    ResponseEntity<String> register(@RequestParam(value="username") String name,@RequestParam(value="password") String pass,@RequestParam(value="mail")String mail) {
        User user=new User(name,pass,mail);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        try {
            if(user.register())
            {
                String json="{"+"\"success\":true,\"message\":"+"\"Use the activation code sent to the university email in order to activate your account and be able to login.\""+"}";
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(json);
            }
            else
            {
                String json="{"+"\"success\":false,\"message\":\""+user.getRegisterError()+"\"}";
                return ResponseEntity.badRequest().headers(headers).body(json);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String json="{"+"\"success\":false,\"message\":\""+user.getRegisterError()+"\"}";
        return ResponseEntity.badRequest().headers(headers).body(json);


    }
    @RequestMapping(value="/activate",method=POST)
    ResponseEntity<String> activate(@RequestParam(value="username") String name,@RequestParam(value="password") String pass,@RequestParam(value="code") String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        User user=new User(name,pass,"");
        if(user.activate(code))
        {
            String json="{"+"\"success\":true,\"session_id\":\""+user.createSession()+"\"}";
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(json);
        }
        else
        {
            String json="{"+"\"success\":false,\"message\":\"Invalid code\"}";
            return ResponseEntity.badRequest().headers(headers).body(json);

        }
    }


}
