package com.mycode.codefellowship.Models;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
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

    @NonNull
    private String fullName;

    @OneToMany(mappedBy = "applicationUser")
    private Set<Post> posts;

    @ManyToMany(cascade = { CascadeType.ALL})
    @JoinTable(
            name = "user_user",
            joinColumns = { @JoinColumn(name = "from_id") },
            inverseJoinColumns = { @JoinColumn(name = "to_id") }
    )
    List<ApplicationUser> following ;

    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    List<ApplicationUser> followers;


    public Long getId() {
        return id;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName() {
        this.fullName = firstName+" "+lastName;
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


    public void setFollowers(ApplicationUser applicationUser){
        this.followers.add(applicationUser);
    }
}
