package com.touryaar.jwt.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(min = 11, max = 11)
    String phoneNo;

    @Size(min = 6, max = 6)
    int generatedOTP;

    boolean passwordReset;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @Column(unique = true)
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @Column(unique = true)
    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private int countryVisited;

    private String location;

}