package com.application.backend.controller;

import com.application.backend.entity.Result;
import com.application.backend.services.AuthService;
import com.application.backend.services.impl.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
public class AuthController {
    @Autowired
    private final AuthService authService=new AuthServiceImpl();
    @GetMapping("/mail")
    public Result captchaSender(String email){
        return authService.emailCaptcha(email);
    }
    @GetMapping("/delete_email")
    public Result deleteAllSubmitCaptchaSender(String email,String token){
        return authService.deleteSubmitCaptcha(email, token);
    };
}
