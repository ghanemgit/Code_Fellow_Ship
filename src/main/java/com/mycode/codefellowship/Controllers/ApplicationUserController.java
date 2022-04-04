package com.mycode.codefellowship.Controllers;


import com.mycode.codefellowship.Models.ApplicationUser;
import com.mycode.codefellowship.Models.Post;
import com.mycode.codefellowship.Repository.ApplicationUserRepository;
import com.mycode.codefellowship.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PostRepository postRepository;




    @GetMapping("/signup")
    public String getSignupPage() {
        return "/signup.html";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(@ModelAttribute ApplicationUser applicationUser) {
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login.html";
    }

    @PostMapping("/perform_login")
    public RedirectView getDashboardPage() {
        return new RedirectView("dashboard");
    }


    @GetMapping("/dashboard")
    public String getDashboard() {
        return "/dashboard.html";
    }




    @GetMapping("/profile")
    public String getUserDetails(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",userDetails.getUsername());
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(userDetails.getUsername());
        applicationUser.setImageUrl("/Assert/img.png");
        model.addAttribute("ImageUrl",applicationUser.getImageUrl());
        model.addAttribute("firstName",applicationUser.getFirstName());
        model.addAttribute("lastName",applicationUser.getLastName());
        model.addAttribute("dataOfBirth",applicationUser.getDateOfBirth());
        model.addAttribute("bio",applicationUser.getBio());
        model.addAttribute("userPosts" , applicationUserRepository.findByUsername(userDetails.getUsername()).getPosts());

        return "profile.html";
    }

    @GetMapping("/users/{id}")
    public String getSpecificUser(@PathVariable Long id,Model model){

        ApplicationUser applicationUser = applicationUserRepository.findById(id).orElseThrow();
        applicationUser.setImageUrl("/Assert/img.png");
        model.addAttribute("ImageUrl",applicationUser.getImageUrl());
        model.addAttribute("firstName",applicationUser.getFirstName());
        model.addAttribute("lastName",applicationUser.getLastName());
        model.addAttribute("dataOfBirth",applicationUser.getDateOfBirth());
        model.addAttribute("bio",applicationUser.getBio());
        return "profile.html";

    }

    @PostMapping("/post")
    public RedirectView createNewPost(@ModelAttribute Post post){

    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    post.setApplicationUser(applicationUserRepository.findByUsername(userDetails.getUsername()));
    postRepository.save(post);
    return new RedirectView("profile");

    }

    @GetMapping("/error")
    public String errorHandler() {
        return "error";
    }
}
