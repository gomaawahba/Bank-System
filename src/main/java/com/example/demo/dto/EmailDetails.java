package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmailDetails {

    private String recipient;

    private String messageBody;

    private String subject;

    private String attachment;
}
