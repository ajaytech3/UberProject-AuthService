package com.example.UberProject_AuthService.dtos;

import com.example.UberProject_AuthService.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupResponseDto {

    private Long id;

    private  String name;

    private  String email;

    private  String password; //encrypted password

    private String phoneNumber;

    private Date created_at;

    public static PassengerSignupResponseDto from(Passenger passenger){
        PassengerSignupResponseDto passengerSignupResponseDto=PassengerSignupResponseDto.builder()
                .id(passenger.getId())
                .email(passenger.getEmail())
                .name(passenger.getName())
                .phoneNumber(passenger.getPhoneNumber())
                .password(passenger.getPassword())
                .created_at(new Date())
                .build();
        return passengerSignupResponseDto;
    }

}
