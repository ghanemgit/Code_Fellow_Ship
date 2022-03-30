package com.mycode.codefellowship.Security;


import com.mycode.codefellowship.Repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("ApplicationUserService")
public class ApplicationUserServiceImp implements UserDetailsService {


    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserServiceImp(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findApplicationUserByUsername(username);

    }
}

