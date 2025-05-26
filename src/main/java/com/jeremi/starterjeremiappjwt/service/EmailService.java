package com.jeremi.starterjeremiappjwt.service;

import com.jeremi.starterjeremiappjwt.model.User;
import com.jeremi.starterjeremiappjwt.strategy.EmailStrategy;

public interface EmailService {

    void sendEmail(User user, String code, EmailStrategy emailStrategy) throws IllegalStateException;

}
