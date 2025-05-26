package com.jeremi.starterjeremiappjwt.service;

import com.jeremi.starterjeremiappjwt.auth.model.AuthenticationRequest;
import com.jeremi.starterjeremiappjwt.auth.model.ResetPasswordRequest;
import com.jeremi.starterjeremiappjwt.model.User;

public interface UserService {

    void confirmResetPassword(AuthenticationRequest request);

    User resetPassword(ResetPasswordRequest request);

}
