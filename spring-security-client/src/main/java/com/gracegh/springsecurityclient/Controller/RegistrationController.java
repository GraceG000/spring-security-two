package com.gracegh.springsecurityclient.Controller;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Model.UserModel;
import com.gracegh.springsecurityclient.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel){
        User user = userService.registerUser(userModel);
        return "Success";
    }
}
