package com.example.demo.service.Impl;

import com.example.demo.dto.EmailDetails;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BankStatement {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private EmailService emailService;
    private static final String FILE="C:\\Users\\MoHaMeD\\pdfProject\\MyStatement.pdf";

    /*
    retrive List of transcation within a dtat range given an account Number
    generate a pdf transaction
    send the file via email
    * */
    public List<Transaction> generateStatment(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        LocalDate start=LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end=LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        log.info("OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        List<Transaction>transactionList=transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start))
                .filter(transaction -> transaction.getModified().isEqual(end)).toList();

        User user=userRepository.findByAcountNumber(accountNumber);
        String customerName=user.getFirstName()+" "+user.getLastName()+" "+user.getOtherName();
        Rectangle statementSize=new Rectangle(PageSize.A4);
        Document document=new Document(statementSize);
        log.info("setting size of document");
        OutputStream outputStream=new FileOutputStream(FILE);

        PdfWriter.getInstance(document,outputStream);
        document.open();
        //design
        PdfPTable bankInfoTable=new PdfPTable(1);
        PdfPCell bankName=new PdfPCell(new Phrase("Bank System"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress=new PdfPCell(new Phrase("AlFashn,Beni Seuif,Egypt"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo=new PdfPTable(2);
        PdfPCell customerInfo=new PdfPCell(new Phrase("Start Date: "+startDate));
        customerInfo.setBorder(0);
        PdfPCell statement=new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);
        PdfPCell stopDate=new PdfPCell(new Phrase("End Date: "+endDate));
        stopDate.setBorder(0);
        PdfPCell name=new PdfPCell(new Phrase("Customer Name: "+customerName));
        name.setBorder(0);
        PdfPCell space=new PdfPCell();
        space.setBorder(0);
        PdfPCell address=new PdfPCell(new Phrase("Customer Address"));
        address.setBorder(0);

        PdfPTable transactionsTable=new PdfPTable(4);
        PdfPCell date=new PdfPCell(new Phrase("Date"));
        date.setBackgroundColor(BaseColor.BLUE);
        date.setBorder(0);
        PdfPCell transactionsType=new PdfPCell(new Phrase("Transactions Type"));
        transactionsType.setBackgroundColor(BaseColor.BLUE);
        transactionsType.setBorder(0);
        PdfPCell transactionAmount=new PdfPCell(new Phrase("Transaction Amount"));
        transactionAmount.setBackgroundColor(BaseColor.BLUE);
        transactionAmount.setBorder(0);
        PdfPCell status=new PdfPCell(new Phrase("Status"));
        status.setBackgroundColor(BaseColor.BLUE);
        status.setBorder(0);
        transactionsTable.addCell(date);
        transactionsTable.addCell(transactionsType);
        transactionsTable.addCell(transactionAmount);
        transactionsTable.addCell(status);

        transactionList.forEach(transaction -> {
            transactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionsTable.addCell(new Phrase(transaction.getStatus()));

        });

        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(endDate);
        statementInfo.addCell(name);
        statementInfo.addCell(space);
        statementInfo.addCell(address);



        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionsTable);

        document.close();

        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested accout Statement attached!!")
                .attachment(FILE)



                .build();
        emailService.sendEmailwithAttachment(emailDetails);

        return transactionList;
    }


//    public void designStatement(List<Transaction> transactions) throws FileNotFoundException, DocumentException {
//
//        Rectangle statementSize=new Rectangle(PageSize.A4);
//        Document document=new Document(statementSize);
//        log.info("setting size of document");
//        OutputStream outputStream=new FileOutputStream(FILE);
//
//        PdfWriter.getInstance(document,outputStream);
//        document.open();
//        //design
//        PdfPTable bankInfoTable=new PdfPTable(1);
//        PdfPCell bankName=new PdfPCell(new Phrase("Bank System"));
//        bankName.setBorder(0);
//        bankName.setBackgroundColor(BaseColor.BLUE);
//        bankName.setPadding(20f);
//
//        PdfPCell bankAddress=new PdfPCell(new Phrase("AlFashn,Beni Seuif,Egypt"));
//        bankAddress.setBorder(0);
//        bankInfoTable.addCell(bankName);
//        bankInfoTable.addCell(bankAddress);
//
//        PdfPTable statementInfo=new PdfPTable(2);
//        PdfPCell customerInf=new PdfPCell("Start Date:");
//
//
//    }




}
