package com.example.demo.service.Impl;

import com.example.demo.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //create Acount take prameter UserRequest and return BankResponse
    BankRsponse createAcount(UserRequest userRequest);
    BankRsponse balanceEnquiry(EnquiryRequest2 enquiryRequest2);

    String nameEnquiry(EnquiryRequest2 enquiryRequest2);

    BankRsponse creditAcount(CreditDebitRequest creditDebitRequest);

    BankRsponse debitAcount(CreditDebitRequest creditDebitRequest);

    BankRsponse tranfer(TransferRequest transferRequest);

}
