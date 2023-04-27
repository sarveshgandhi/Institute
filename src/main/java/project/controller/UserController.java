package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import project.service.UserProfileService;

@RestController
public class UserController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    public String authenticate() {
        return null;
    }
}
