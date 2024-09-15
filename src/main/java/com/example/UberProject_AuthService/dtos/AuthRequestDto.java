package com.example.UberProject_AuthService.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    private  String email;
    private String password;

}
