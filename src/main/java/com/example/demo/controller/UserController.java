package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name="User Account Management APIs ")
public class UserController {
    //http://localhost:3000/api/user
    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Operation(
            summary = "Create New User Accout",
            description = "Creating a new user and assigining an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )

    @PostMapping
    public BankRsponse createAcount(@RequestBody UserRequest userRequest){
        return userService.createAcount(userRequest);
    }
    @PostMapping("/login")
    public BankRsponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);

    }

    //http://localhost:3000/api/user/balanceEnquiry
    @Operation(
            summary = "Balance Enquiry",
            description = "Given an Account Number,check how much the user has"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/balanceEnquiry")
    public BankRsponse balanceEnquiry(@RequestBody EnquiryRequest2 enquiryRequest2){
        return userService.balanceEnquiry(enquiryRequest2);
    }

    //http://localhost:3000/api/user/nameEnquiry


    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest2 enquiryRequest2){
        return userService.nameEnquiry(enquiryRequest2);

    }

    //http://localhost:3000/api/user/credit

    //credit
    @PostMapping("/credit")
    public BankRsponse creditAcount(@RequestBody CreditDebitRequest creditDebitRequest){

        return userService.creditAcount(creditDebitRequest);
    }

    //debit
    //http://localhost:3000/api/user/depit
    @PostMapping("/debit")
    public BankRsponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){

        return userService.debitAcount(creditDebitRequest);
    }

    //transfer
    //http://localhost:3000/api/user/transfer
    @PostMapping("/transfer")
    public BankRsponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.tranfer(transferRequest);
    }








}
