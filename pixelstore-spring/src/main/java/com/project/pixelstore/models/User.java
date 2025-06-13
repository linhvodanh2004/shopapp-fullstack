package com.project.pixelstore.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")

public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String username;
    @Column(name = "fullname", length = 100)
    private String fullName;
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Column(length = 100)
    private String email;
    @Column(length = 200)
    private String address;
    @Column(length = 100)
    private String state;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String country;
    @Column(length = 200)
    private String postalCode;
    @Column(length = 200, nullable = false)
    private String password;

    public boolean active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
