package com.mycode.codefellowship.Models;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String dateOfBirth;

    @NonNull
    private String bio;

    @NonNull
    private String imageUrl;

    public ApplicationUser() {
    }

    public ApplicationUser(String username, @NonNull String password, @NonNull String firstName, @NonNull String lastName, @NonNull String dateOfBirth, @NonNull String bio, @NonNull String imageUrl) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return ("Welcome, " + username + " Your info is\nFirst Name : "+ firstName +"\nLast Name : " + lastName +"\nDate of Birth : " + dateOfBirth + "\nBio : " + bio);
    }

    @OneToMany(mappedBy = "applicationUser")
    private Set<Post> posts;
}
