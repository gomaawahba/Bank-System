package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.PrivateKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    //transfer funds from account to account we should hava sourceAccountNumber and destinationAccountNumber
    private String sourceAccountNumber;

    private String destinationAccountNumber;

    private BigDecimal amount;
}
