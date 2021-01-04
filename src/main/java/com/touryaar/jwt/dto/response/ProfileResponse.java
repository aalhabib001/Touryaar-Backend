package com.touryaar.jwt.dto.response;

import com.touryaar.jwt.model.Role;
import lombok.*;
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
public class ProfileResponse {
    String phoneNo;

    private String firstName;

    private String lastName;

    private String email;

    private int countryVisited;

    private String location;
}
