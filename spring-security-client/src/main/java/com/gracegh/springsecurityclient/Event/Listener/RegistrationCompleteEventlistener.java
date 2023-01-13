package com.gracegh.springsecurityclient.Event.Listener;

import com.gracegh.springsecurityclient.Entity.User;
import com.gracegh.springsecurityclient.Event.RegistrationComplete;
import com.gracegh.springsecurityclient.Service.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;


@Slf4j
@Getter
@Setter
public class RegistrationCompleteEventlistener implements ApplicationListener<RegistrationComplete> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationComplete event) {
        //Create the Verification Token for the User with a link...
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        //send mail to user...
        String url = event.getApplicationUrl() + "verifyReegistration?token=" + token;

        //sendVerificationEmail()...

        log.info("Click the link to verify your account: {}");
    }
}
