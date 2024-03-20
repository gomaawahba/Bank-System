package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    @Column(name = "transactionType")
    private String transactionType;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDate createdAt;
    @UpdateTimestamp
    @Column(name = "modified")
    private LocalDate modified;
}
