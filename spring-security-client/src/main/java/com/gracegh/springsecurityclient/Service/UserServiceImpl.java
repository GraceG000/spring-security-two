package com.gracegh.springsecurityclient.Service;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Entity.VerificationToken;
import com.gracegh.springsecurityclient.Model.UserModel;
import com.gracegh.springsecurityclient.Repository.UserRepository;
import com.gracegh.springsecurityclient.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel){
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("User");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user){
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token){
     VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

     if(verificationToken == null){
         return "invalid";
     }

     User user = verificationToken.getUser();
     Calendar cal = Calendar.getInstance();

     if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
         verificationTokenRepository.delete(verificationToken);
         return "expired";
     }

     user.setEnabled(true);
     userRepository.save(user);
     return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }
}
