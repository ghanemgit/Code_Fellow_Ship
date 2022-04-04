package com.mycode.codefellowship.Security;


import com.mycode.codefellowship.Models.ApplicationUser;
import com.mycode.codefellowship.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("ApplicationUserService")
public class ApplicationUserServiceImp implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        if (applicationUser == null) {
            System.out.print("Username not found");
            throw new UsernameNotFoundException((username + " not found"));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return applicationUser;
    }
}

