package com.gracegh.springsecurityclient.Controller;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Entity.VerificationToken;
import com.gracegh.springsecurityclient.Event.RegistrationComplete;
import com.gracegh.springsecurityclient.Model.PasswordModel;
import com.gracegh.springsecurityclient.Model.UserModel;
import com.gracegh.springsecurityclient.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

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

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
          User user = userService.findUserByEmail(passwordModel.getEmail());
          String url ="";

          if(user!=null){
              String token = UUID.randomUUID().toString();
              userService.createPasswordResetTokenForUser(user, token);

              url = passwordResetTokenMail(user, applicationUrl(request), token);

          }

          return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.validatePasswordResetToken(token);

        if(!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password has been reset successfully";
        }else {
            return "Invalid Token";
        }
    }



    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;

        //send verificationEmail()
        log.info("Click the link to reset your password: {}", url);

        return url;
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
