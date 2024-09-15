package com.example.UberProject_AuthService.controllers;


import com.example.UberProject_AuthService.dtos.AuthRequestDto;
import com.example.UberProject_AuthService.dtos.AuthResponseDto;
import com.example.UberProject_AuthService.dtos.PassengerSignupRequestDto;
import com.example.UberProject_AuthService.dtos.PassengerSignupResponseDto;
import com.example.UberProject_AuthService.services.AuthService;
import com.example.UberProject_AuthService.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    private AuthService authService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerSignupResponseDto> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerSignupResponseDto passengerSignupResponseDto = authService.signupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(passengerSignupResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signin(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        System.out.println("Request received"+authRequestDto.getEmail()+"  "+authRequestDto.getPassword());
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
       if(authentication.isAuthenticated()){
           Map<String,Object> payload=new HashMap<>();
           payload.put("email", authRequestDto.getEmail());

           String jwttoken=jwtService.createToken(authRequestDto.getEmail());
           ResponseCookie cookie=ResponseCookie.from("JwtToken",jwttoken)
                           .httpOnly(true) //if you make httpOnly as true then client/user/UI will not able access or see the token
                           .secure(false)
                           .maxAge(cookieExpiry)
                            .build();
           response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
           return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(),HttpStatus.OK);
       }else {
          throw  new UsernameNotFoundException("user not found");
       }
    }

    @GetMapping("/validate")
    public  ResponseEntity<?> validate(HttpServletRequest request){
        for(Cookie cookie : request.getCookies()){
            System.out.println(cookie.getName()+" "+cookie.getValue());
        }
        return  new ResponseEntity<>("Success",HttpStatus.OK);
    }

}
