package com.example.UberProject_AuthService.services;

/*
* This class is responsible for loading the user in the form of userDetails object for auth
* */

import com.example.UberProject_AuthService.helpers.AuthPassengerDetails;
import com.example.UberProject_AuthService.models.Passenger;
import com.example.UberProject_AuthService.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger=passengerRepository.findPassengerByEmail(email);
        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }else {
            throw new UsernameNotFoundException("Cannot found the User by username");
        }
    }
}
