package com.mycode.codefellowship.Controllers;


import com.mycode.codefellowship.Models.ApplicationUser;
import com.mycode.codefellowship.Models.Post;
import com.mycode.codefellowship.Repository.ApplicationUserRepository;
import com.mycode.codefellowship.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/signup.html";
        }

        return "redirect:/profile";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(@ModelAttribute ApplicationUser applicationUser) {
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
        return new RedirectView("/login");
    }



    @GetMapping("/login")
    public String showLoginForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login.html";
        }

        return "redirect:/profile";
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
        applicationUser.setImageUrl("/css/Assert/img.png");
        model.addAttribute("ImageUrl",applicationUser.getImageUrl());
        model.addAttribute("firstName",applicationUser.getFirstName());
        model.addAttribute("lastName",applicationUser.getLastName());
        model.addAttribute("dataOfBirth",applicationUser.getDateOfBirth());
        model.addAttribute("bio",applicationUser.getBio());
        applicationUser.setFullName(applicationUser.getFirstName()+" "+applicationUser.getLastName());
        model.addAttribute("fullName",applicationUser.getFullName());
        model.addAttribute("userPosts" , applicationUserRepository.findByUsername(userDetails.getUsername()).getPosts());
//        Post posts = postRepository.findAllByApplicationUserId(applicationUser.getId());
//        model.addAttribute("posts",posts);

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



    @GetMapping("/error")
    public String errorHandler() {
        return "error";
    }
}
