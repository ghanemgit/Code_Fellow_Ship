package com.mycode.codefellowship.Controllers;


import com.mycode.codefellowship.Models.ApplicationUser;
import com.mycode.codefellowship.Models.Post;
import com.mycode.codefellowship.Repository.ApplicationUserRepository;
import com.mycode.codefellowship.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;



    @PostMapping("/post")
    public RedirectView createNewPost(@ModelAttribute Post post){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setApplicationUser(applicationUserRepository.findByUsername(userDetails.getUsername()));
        postRepository.save(post);
        return new RedirectView("/profile");
    }


}
