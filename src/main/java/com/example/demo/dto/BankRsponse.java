package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankRsponse {

    private String responseCode;

    private String responseMessage;

    private AcountInfo acountInfo;
}
