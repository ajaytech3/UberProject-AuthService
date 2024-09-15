package com.example.UberProject_AuthService.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDto {

    private String id;

    private String email;

    private String password;

    private String phoneNumber;

    private String name;
}
