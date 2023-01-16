package com.gracegh.springsecurityclient.Service;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Entity.VerificationToken;
import com.gracegh.springsecurityclient.Model.UserModel;

import java.util.Optional;

public interface UserService {
   public User registerUser(UserModel userModel);

    public void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

 String validatePasswordResetToken(String token);

 Optional<User> getUserByPasswordResetToken(String token);

 void changePassword(User user, String newPassword);
}
