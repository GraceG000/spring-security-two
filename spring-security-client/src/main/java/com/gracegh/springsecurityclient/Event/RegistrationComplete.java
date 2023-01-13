package com.gracegh.springsecurityclient.Event;

import com.gracegh.springsecurityclient.Entity.User;
import org.springframework.context.ApplicationEvent;

public class RegistrationComplete extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public RegistrationComplete(User user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }

    public User getUser() {
        return user;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }
}
