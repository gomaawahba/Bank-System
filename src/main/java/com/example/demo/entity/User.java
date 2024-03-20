package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "firstName")
    private  String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "otherName")
    private String otherName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "address")
    private String address;
    @Column(name = "stateOfOrigine")
    private String stateOfOrigine;
    @Column(name = "acountNumber")
    private String acountNumber;
    @Column(name = "accountBalance")
    private BigDecimal accountBalance;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "alternativePhoneNumber")
    private String alternativePhoneNumber;
    @Column(name = "status")
    private String status;

    private Role role;

    //created time
    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDate createdAt;


    @UpdateTimestamp
    @Column(name = "modified")
    private LocalDate modified;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
