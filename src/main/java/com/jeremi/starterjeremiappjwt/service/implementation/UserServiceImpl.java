package com.jeremi.starterjeremiappjwt.service.implementation;

import com.jeremi.starterjeremiappjwt.auth.model.AuthenticationRequest;
import com.jeremi.starterjeremiappjwt.auth.model.ResetPasswordRequest;
import com.jeremi.starterjeremiappjwt.auth.service.AuthenticationService;
import com.jeremi.starterjeremiappjwt.model.User;
import com.jeremi.starterjeremiappjwt.model.VerificationCode;
import com.jeremi.starterjeremiappjwt.repository.UserRepository;
import com.jeremi.starterjeremiappjwt.repository.VerificationCodeRepository;
import com.jeremi.starterjeremiappjwt.service.EmailService;
import com.jeremi.starterjeremiappjwt.service.UserService;
import com.jeremi.starterjeremiappjwt.strategy.implementation.ResetPasswordEmailStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.jeremi.starterjeremiappjwt.exception.ConstantMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthenticationService authenticationService;
    private final EmailService emailService;


    /**
     * Confirme la réinitialisation du mot de passe en envoyant un code de réinitialisation à l'utilisateur.
     *
     * @param request la demande d'authentification contenant l'email de l'utilisateur
     *
     * @throws NoSuchElementException si l'utilisateur correspondant à l'email n'est pas trouvé
     */
    @Override
    public void confirmResetPassword(AuthenticationRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new NoSuchElementException(ITEM_NOT_FOUND));

        String code = authenticationService.generatesCode(user);
        ResetPasswordEmailStrategy resetPasswordEmailStrategy = new ResetPasswordEmailStrategy();
        emailService.sendEmail(user, code, resetPasswordEmailStrategy);
    }

    /**
     * Réinitialise le mot de passe de l'utilisateur en utilisant le code de vérification fourni.
     *
     * @param request la demande de réinitialisation de mot de passe contenant l'email, le code et le nouveau mot de passe
     * @return l'utilisateur mis à jour avec le nouveau mot de passe
     *
     * @throws NoSuchElementException si le code de vérification est invalide ou si l'utilisateur correspondant à l'email n'est pas trouvé
     * @throws OptimisticLockingFailureException si une exception de verrouillage optimiste se produit lors de l'enregistrement du mot de passe
     */
    @Override
    public User resetPassword(ResetPasswordRequest request) {
        VerificationCode verificationCode = verificationCodeRepository.findByCode(request.getCode());
        if(verificationCode == null) {
            throw new NoSuchElementException(CODE_EMAIL_INVALID);
        }

        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new NoSuchElementException(ITEM_NOT_FOUND));

        try {
            user.setPassword(passwordEncoder.encode(request.getResetPassword()));
            user = userRepository.saveAndFlush(user);
        } catch (OptimisticLockingFailureException e) {
            throw new OptimisticLockingFailureException(OPTIMISTIC_LOCKING_EXCEPTION);
        }

        return user;
    }

}
