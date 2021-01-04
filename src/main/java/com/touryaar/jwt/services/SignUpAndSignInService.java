package com.touryaar.jwt.services;



import com.touryaar.jwt.Util.RoleUtils;
import com.touryaar.jwt.dto.request.LoginForm;
import com.touryaar.jwt.dto.request.SignUpForm;
import com.touryaar.jwt.dto.response.JwtResponse;
import com.touryaar.jwt.dto.response.ProfileResponse;
import com.touryaar.jwt.dto.response.UserResponse;
import com.touryaar.jwt.model.User;
import com.touryaar.jwt.repository.UserRepository;
import com.touryaar.jwt.security.jwt.JwtProvider;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor

@Service
public class SignUpAndSignInService {

    PasswordEncoder encoder;
    JwtProvider jwtProvider;
    AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleUtils roleUtils;


    public ResponseEntity<JwtResponse> signUp(SignUpForm signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Email Already Exists");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getEmail() + signUpRequest.getPhoneNo());
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNo(signUpRequest.getPhoneNo());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setLocation(signUpRequest.getLocation());
        user.setCountryVisited(signUpRequest.getCountryVisited());
        user.setRoles(roleUtils.getRolesFromStringToRole(signUpRequest.getRole()));
        userRepository.saveAndFlush(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        signUpRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return new ResponseEntity<>(new JwtResponse(jwt, signUpRequest.getRole()), HttpStatus.OK);
    }


    public ResponseEntity<JwtResponse> signIn(LoginForm loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        String userName;
        if (userOptional.isPresent()) {
            userName = userOptional.get().getUsername();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Not Exists");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        return new ResponseEntity<>(new JwtResponse(jwt, roleUtils.getRolesStringFromRole(userOptional.get().getRoles())), HttpStatus.OK);
    }

    public ResponseEntity<ProfileResponse> getProfile() throws NotFoundException, AuthenticationException {

        Object authUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authUser instanceof UserDetails) {
            String username = ((UserDetails) authUser).getUsername();

            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                ProfileResponse profile = new ProfileResponse(user.getPhoneNo(), user.getFirstName(),
                        user.getLastName(),  user.getEmail(), user.getCountryVisited(),
                        user.getLocation());

                return new ResponseEntity<>(profile, HttpStatus.OK);


            } else {
                throw new NotFoundException("User Not Found");
            }

        } else {
            throw new AuthenticationException("Authentication Error");
        }

    }

}
