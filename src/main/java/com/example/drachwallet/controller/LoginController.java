package com.example.drachwallet.controller;

import com.example.drachwallet.exceptions.LoginException;
import com.example.drachwallet.model.Login;
import com.example.drachwallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginMapping(@RequestBody Login login) throws LoginException {

        String output = loginService.login(login);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutMapping(@RequestParam String key) throws LoginException{

        String output = loginService.logout(key);

        return new ResponseEntity<String>(output,HttpStatus.OK);
    }
}
