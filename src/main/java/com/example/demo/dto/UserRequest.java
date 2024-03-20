package com.example.demo.dto;

import lombok.*;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    private  String firstName;

    private String lastName;

    private String otherName;

    private String gender;

    private String address;

    private String stateOfOrigine;

    private String acountNumber;

    private BigDecimal accountBalance;

    private String email;

    private String password;

    private String phoneNumber;

    private String alternativePhoneNumber;


}
