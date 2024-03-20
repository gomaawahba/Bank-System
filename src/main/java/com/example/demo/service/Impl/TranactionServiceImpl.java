package com.example.demo.service.Impl;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    @Autowired
    public TranactionServiceImpl(TransactionRepository transactionRepository) {
        
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTranscation(TransactionDTO transactionDTO) {
        Transaction transaction= Transaction.builder()
                .transactionType(transactionDTO.getTransactionType())
                .accountNumber(transactionDTO.getAccountNumber())
                .amount(transactionDTO.getAmount())
                .status("SUCCESS")
                .build();

        //to Save
        transactionRepository.save(transaction);
        System.out.println("Transcation saved Successfylly");



    }
}
