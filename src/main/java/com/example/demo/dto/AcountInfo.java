package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AcountInfo {
    @Schema(
            name = "User Account name"
    )

    private String acountName;
    @Schema(
            name = "User Account balance"
    )

    private BigDecimal accountBalance;
    @Schema(
            name = "User Account number"
    )

    private String accountNumber;
}
