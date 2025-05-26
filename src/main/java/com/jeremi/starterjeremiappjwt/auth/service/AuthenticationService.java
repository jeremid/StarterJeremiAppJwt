package com.jeremi.starterjeremiappjwt.auth.service;

import com.jeremi.starterjeremiappjwt.auth.model.AuthenticationRequest;
import com.jeremi.starterjeremiappjwt.auth.model.AuthenticationResponse;
import com.jeremi.starterjeremiappjwt.auth.model.RegistrationRequest;
import com.jeremi.starterjeremiappjwt.model.User;
import com.jeremi.starterjeremiappjwt.model.VerificationCode;

public interface AuthenticationService {

    void register(RegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    VerificationCode verifyAccount(String code);

    String generatesCode(User user);

    User regenerateCode(AuthenticationRequest request);

}
