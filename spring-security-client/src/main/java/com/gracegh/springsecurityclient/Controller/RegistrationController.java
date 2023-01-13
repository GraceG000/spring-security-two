package com.gracegh.springsecurityclient.Controller;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Event.RegistrationComplete;
import com.gracegh.springsecurityclient.Model.UserModel;
import com.gracegh.springsecurityclient.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    //creating an event...
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationComplete(
                user,
                applicationUrl(request)
        ));
        return "Success";
    }

    private String applicationUrl(HttpServletRequest request) {
        return  "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
