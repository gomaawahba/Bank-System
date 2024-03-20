package com.example.demo.controller;

import com.example.demo.entity.Transaction;
import com.example.demo.service.Impl.BankStatement;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/bankStatement")
public class TransactionController {

    //http://localhost:3000/bankStatement
    private BankStatement bankStatement;

    @Autowired
    public TransactionController(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    //http://localhost:3000/bankStatement/statement
    @GetMapping("/statement")
    public List<Transaction>generateStatement(@RequestParam String accountNumber,
                                              @RequestParam String startDate,
                                              @RequestParam String endDate) throws DocumentException, FileNotFoundException {

        return bankStatement.generateStatment(accountNumber, startDate, endDate);
    }

}
