package com.mycode.codefellowship.Repository;

import com.mycode.codefellowship.Models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findApplicationUserByFirstNameAndLastName(String firstNAme, String lastName);

    ApplicationUser findByUsername(String username);

}
