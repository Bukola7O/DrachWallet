package com.example.drachwallet.service;

import com.example.drachwallet.exceptions.LoginException;
import com.example.drachwallet.model.Login;

public interface LoginService {
    public String login (Login login) throws LoginException;

    public String logout (String Key) throws LoginException;
}
