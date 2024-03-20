package com.example.demo.service.Impl;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.*;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.AccountUtils;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private EmailService emailService;
    private TransactionService transactionService;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService, TransactionService transactionService,PasswordEncoder passwordEncoder
    ,AuthenticationManager authenticationManager,JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.transactionService = transactionService;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    /*
    *   first Check if user already have been account or not
         craete New Acount and save in db
    * */


    @Override
    public BankRsponse createAcount(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())){
            BankRsponse bankRsponse=BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .acountInfo(null)
                    .build();
            return bankRsponse;
        }
        User user=User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigine(userRequest.getStateOfOrigine())
                .acountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .role(Role.valueOf("ROLE_ADMIN"))

                .build();
        //save account
        User saveduser=userRepository.save(user);
        //Send email Alart
        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(saveduser.getEmail())
                .subject("ACOUNT CREATION SUCCESS")
                .messageBody("Congratulation! Your Acount has been Successfully Created.\nYour Acount Details:\n" +
                        "Acoutn Name: " +saveduser.getFirstName()+" "+saveduser.getLastName()+" "+saveduser.getOtherName()
                +"\nAcount Number: "+saveduser.getAcountNumber())


                .build();
        emailService.sendEmailAlart(emailDetails);



        return BankRsponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATE_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATED_MESSAGE)
                .acountInfo(AcountInfo.builder()
                        .accountBalance(saveduser.getAccountBalance())
                        .accountNumber(saveduser.getAcountNumber())
                        .acountName(saveduser.getFirstName()+" "+saveduser.getLastName()+" "+saveduser.getOtherName())
                        .build())
                .build();
    }

    public BankRsponse login(LoginDto loginDto){
        Authentication authentication=null;
        authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        EmailDetails loginAlert= EmailDetails.builder()
                .subject("You are loggied in!!")
                .recipient(loginDto.getEmail())
                .messageBody("You logged into your account,if you did not initiate this request,please contact your bank")
                .build();
        emailService.sendEmailAlart(loginAlert);

        return BankRsponse.builder()
                .responseCode("Login Success")
                .responseMessage(jwtTokenProvider.generateToken(authentication))

                .build();

    }
    @Override
    public BankRsponse balanceEnquiry(EnquiryRequest2 enquiryRequest2) {

        //check if account Number or not
        boolean isAcountExist=userRepository.existsByAcountNumber(enquiryRequest2.getAccountNumber());
        if(!isAcountExist){
            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .acountInfo(null)
                    .build();
        }
        //user Have account Number already

        User foundUser=userRepository.findByAcountNumber(enquiryRequest2.getAccountNumber());
        return BankRsponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .acountInfo(AcountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(enquiryRequest2.getAccountNumber())
                        .acountName(foundUser.getFirstName()+" "+foundUser.getLastName()+" "+foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest2 enquiryRequest2) {
        //check if account Number or not
        boolean isAcountExist=userRepository.existsByAcountNumber(enquiryRequest2.getAccountNumber());
        if(!isAcountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User foundUser=userRepository.findByAcountNumber(enquiryRequest2.getAccountNumber());

        return foundUser.getFirstName()+" "+foundUser.getLastName()+" "+foundUser.getOtherName();
    }

    @Override
    public BankRsponse creditAcount(CreditDebitRequest creditDebitRequest) {
        //check if account Number or not
        boolean isAcountExist=userRepository.existsByAcountNumber(creditDebitRequest.getAccountNumber());
        if(!isAcountExist){
            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .acountInfo(null)
                    .build();
        }

        User userToCredit=userRepository.findByAcountNumber(creditDebitRequest.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
        //to save
        userRepository.save(userToCredit);

        return BankRsponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDIRED_SUCCESS_MESSAGE)
                .acountInfo(AcountInfo.builder()
                        .acountName(userToCredit.getFirstName()+" "+userToCredit.getLastName()+" "+userToCredit.getOtherName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(creditDebitRequest.getAccountNumber())

                        .build())

                .build();

    }

    @Override
    public BankRsponse debitAcount(CreditDebitRequest creditDebitRequest) {
        //check if account Number or not
        //check if the amount you intend to withdraw is not more than account balance
        boolean isAcountExist=userRepository.existsByAcountNumber(creditDebitRequest.getAccountNumber());
        if(!isAcountExist){
            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .acountInfo(null)
                    .build();
        }

        User userToDebit=userRepository.findByAcountNumber(creditDebitRequest.getAccountNumber());
        //check if the amount you intend to withdraw is not more than account balance
        //(userToDebit.getAccountBalance() < creditDebitRequest.getAmount() when i write this in if give error
        //becuse if is not avalble for bigdecimal

        //we convert bigDecimal to integer
        BigInteger avaliableBalance=userToDebit.getAccountBalance().toBigInteger();

        BigInteger avaliableamount=creditDebitRequest.getAmount().toBigInteger();


        if(avaliableBalance.intValue() < avaliableamount.intValue()){
            return BankRsponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .acountInfo(null)
                    .build();

        }else{

            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
            userRepository.save(userToDebit);

            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITRD_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .acountInfo(AcountInfo.builder()
                            .accountNumber(creditDebitRequest.getAccountNumber())
                            .acountName(userToDebit.getFirstName()+" "+userToDebit.getLastName()+" "+userToDebit.getOtherName())
                            .accountBalance(userToDebit.getAccountBalance())

                            .build())

                    .build();

        }
    }

    @Override
    public BankRsponse tranfer(TransferRequest transferRequest) {
        //first to transer fund 1.check sourceAccount isexist or not && destinachAccount
        //check sourceAccount
        boolean sourceAccount=userRepository.existsByAcountNumber(transferRequest.getSourceAccountNumber());
        //check for destinshan Account!
        boolean destinationAccountNumber=userRepository.existsByAcountNumber(transferRequest.getDestinationAccountNumber());
        if(!sourceAccount){
            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .acountInfo(null)
                    .build();
        }
        else if(!destinationAccountNumber){
            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DESTINATION_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DESTINATION_NOT_EXIST_MASSEAGE)
                    .acountInfo(null)
                    .build();

        }
        else{
            //if sourceAccount && destinationAccountNumber exist
            User sourceAccountUserBalance=userRepository.findByAcountNumber(transferRequest.getSourceAccountNumber());
            User destinationAccountUserBalance=userRepository.findByAcountNumber(transferRequest.getDestinationAccountNumber());
            //after u get accountBalance from source code you subtract from sourceAccount
            //check amount&& aAccountBalance
            //we convert bigDecimal to integer
            BigInteger avaliableBalance=sourceAccountUserBalance.getAccountBalance().toBigInteger();
            BigInteger avaliableamount=transferRequest.getAmount().toBigInteger();
            //to check AccountBalance in db and amount user he want transfer
//            System.out.println(avaliableBalance.intValue());
//            System.out.println(avaliableamount.intValue());

            if(avaliableBalance.intValue() < avaliableamount.intValue()){
                return BankRsponse.builder()
                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                        .acountInfo(null)
                        .build();

            }
            sourceAccountUserBalance.setAccountBalance(sourceAccountUserBalance.getAccountBalance().subtract(transferRequest.getAmount()));
//            System.out.println(sourceAccountUserBalance.getAccountBalance().subtract(transferRequest.getAmount()));
            userRepository.save(sourceAccountUserBalance);
            //after subtract from sourceAccount we add amount
            destinationAccountUserBalance.setAccountBalance(destinationAccountUserBalance.getAccountBalance().add(transferRequest.getAmount()));

//            System.out.println(destinationAccountUserBalance.getAccountBalance().add(transferRequest.getAmount()));
           userRepository.save(destinationAccountUserBalance);
           //Save Transaction
            TransactionDTO transactionDTO= TransactionDTO.builder()
                    .accountNumber(destinationAccountUserBalance.getAcountNumber())
                    .transactionType("CREDIT")
                    .amount(transferRequest.getAmount())


                    .build();
            transactionService.saveTranscation(transactionDTO);
           //to sent email request
            EmailDetails emailDetails= EmailDetails.builder()
                    .recipient(destinationAccountUserBalance.getEmail())
                    .subject("transfer SUCCESS")
                    .messageBody("Many transfer Successfully .\nYour Acount Details:\n" +
                            "Acoutn Name: " +destinationAccountUserBalance.getFirstName()+" "+destinationAccountUserBalance.getLastName()+" "+destinationAccountUserBalance.getOtherName()
                            +"\nAcount Number: "+destinationAccountUserBalance.getAcountNumber()+"\namount: "+transferRequest.getAmount())

                    .build();
            emailService.sendEmailAlart(emailDetails);
            System.out.println("Email Sent Successfully after process transfer ");
            return BankRsponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESS_MESSAGE)
                    .acountInfo(AcountInfo.builder()
                            .accountBalance(sourceAccountUserBalance.getAccountBalance())
                            .accountNumber(sourceAccountUserBalance.getAcountNumber())
                            .acountName(sourceAccountUserBalance.getFirstName()+" "+sourceAccountUserBalance.getLastName()+" "+sourceAccountUserBalance.getOtherName())

                            .build())
                    .build();

        }
        //secound check for amount is less then AccontBalance
    }
}
