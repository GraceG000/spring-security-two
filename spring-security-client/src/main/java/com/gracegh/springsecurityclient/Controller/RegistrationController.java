package com.gracegh.springsecurityclient.Controller;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Entity.VerificationToken;
import com.gracegh.springsecurityclient.Event.RegistrationComplete;
import com.gracegh.springsecurityclient.Model.UserModel;
import com.gracegh.springsecurityclient.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
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

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
         String result = userService.validateVerificationToken(token);
         if(result.equalsIgnoreCase("valid")){
             return "User was successfully verified!";
         }
         return "Bad user";
    }

    @GetMapping("/resendToken")
    public String resendVerification(@RequestParam("token") String oldToken, HttpServletRequest request){

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

        User user = verificationToken.getUser();
        resendVerificationMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent";
    }

    private void resendVerificationMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        //send verificationEmail()
        log.info("Click the link to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return  "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
