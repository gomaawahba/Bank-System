package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDTO {
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;

}
