package com.mycode.codefellowship.Controllers;


import com.mycode.codefellowship.Models.ApplicationUser;
import com.mycode.codefellowship.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserController(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }




    @GetMapping("/signup")
    public String getSignupPage() {
        return "/signup.html";
    }

    @PostMapping("/signup")
    public RedirectView signupUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String bio
    ) {
        ApplicationUser applicationUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, dateOfBirth, bio);
        applicationUserRepository.save(applicationUser);
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login.html";
    }

    @PostMapping("/login")
    public RedirectView userLogin(HttpServletRequest request, String username, String password) {
        return new RedirectView();
    }


}
