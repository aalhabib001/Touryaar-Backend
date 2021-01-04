package com.touryaar.jwt.controller;


import com.touryaar.jwt.dto.request.LoginForm;
import com.touryaar.jwt.dto.request.SignUpForm;
import com.touryaar.jwt.dto.response.DataResponse;
import com.touryaar.jwt.dto.response.JwtResponse;
import com.touryaar.jwt.dto.response.ProfileResponse;
import com.touryaar.jwt.services.SignUpAndSignInService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@AllArgsConstructor

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SignUpAndSignInService signUpAndSignInService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return signUpAndSignInService.signIn(loginRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignUpForm signUpRequest){
        return signUpAndSignInService.signUp(signUpRequest);
    }

    @GetMapping("/data")
    public DataResponse getData(){
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData("Hello World");

        return dataResponse;
    }

    @GetMapping("/user/profile")
    public ResponseEntity<ProfileResponse> getProfile() throws NotFoundException, AuthenticationException {
        return signUpAndSignInService.getProfile();
    }

}
