package com.example.demo.service.Impl;

import com.example.demo.dto.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{


    //inject JavaMailSender
    private JavaMailSender javaMailSender;
    //username email
    @Value("${spring.mail.username}")
    private String senderEmail;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailAlart(EmailDetails emailDetails) {
        try {
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessageBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            //to send
            javaMailSender.send(simpleMailMessage);
            //check message send successfully or not print on console
            System.out.println("Mail sent successfully");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailwithAttachment(EmailDetails emailDetails) {

        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();



        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom("gabdo7403@gmail.com");
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            FileSystemResource file=new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(Objects.requireNonNull(file.getFilename())), file);
            javaMailSender.send(mimeMailMessage);

            log.info(file.getFilename()+" has been sent to user with email"+emailDetails.getRecipient());


        }catch (MessagingException e){
            throw new RuntimeException(e);
        }



    }


}
