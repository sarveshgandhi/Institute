package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.service.UserProfileService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @PostMapping("/readJson")
    public String readUsers(@RequestParam("file") MultipartFile file) {
        return userProfileService.readUsers(file);
    }

    @PostMapping("/authenticate")
    public String authenticate() {

        return null;
    }
}
