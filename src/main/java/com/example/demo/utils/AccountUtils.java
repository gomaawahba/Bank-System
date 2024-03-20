package com.example.demo.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE="001";

    public static final String ACCOUNT_EXISTS_MESSAGE="this user already has an account Created!";

    public static final String ACCOUNT_CREATE_SUCCESS="002";

    public static final String ACCOUNT_CREATED_MESSAGE="Account has been Successfully Created";

    public static final String ACCOUNT_NOT_EXIST_CODE="003";

    public static final String ACCOUNT_NOT_EXIST_MESSAGE="User with the provided Account Number Not exist!!";

    public static final String ACCOUNT_FOUND_CODE="004";

    public static final String ACCOUNT_FOUND_MESSAGE="User Account Found!";


    public static final String ACCOUNT_CREDITED_SUCCESS="005";

    public static final String ACCOUNT_CREDIRED_SUCCESS_MESSAGE="User Account Credit Successfully!";

    public static final String INSUFFICIENT_BALANCE_CODE="006";


    public static final String INSUFFICIENT_BALANCE_MESSAGE="Insufficient balance!";

    public static final String ACCOUNT_DEBITRD_SUCCESS="007";

    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE="User Account Debited Successfully!";

    public static final String ACCOUNT_DESTINATION_NOT_EXIST_CODE="008";

    public static final String ACCOUNT_DESTINATION_NOT_EXIST_MASSEAGE="User Account You Want to Transfer funds Not exist!";

    public static final String ACCOUNT_TRANSFER_SUCCESS_CODE="009";

    public static final String ACCOUNT_TRANSFER_SUCCESS_MESSAGE="Transfer funds successfully!!";



    public static String generateAccountNumber(){

        //this years +andomizedsixdigits

        Year currentyear= Year.now();

        //min
        int min=100000;

        //max
        int max=999999;

        //generte  random number between min and max

        int randNumber= (int) Math.floor(Math.random()*(max-min+1)+min);

        //convert the current and randomNumber to String then concatenat Them
        String year=String.valueOf(currentyear);

        String randomNumber=String.valueOf(randNumber);

        StringBuffer accountNumber=new StringBuffer();


        return accountNumber.append(year).append(randomNumber).toString();

    }
}
