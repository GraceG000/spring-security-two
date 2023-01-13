package com.gracegh.springsecurityclient.Service;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Model.UserModel;

public interface UserService {
   public User registerUser(UserModel userModel);

    public void saveVerificationTokenForUser(String token, User user);
}
