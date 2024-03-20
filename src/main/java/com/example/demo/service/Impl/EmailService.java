package com.example.demo.service.Impl;

import com.example.demo.dto.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendEmailAlart(EmailDetails emailDetails);

    public void sendEmailwithAttachment(EmailDetails emailDetails);
}
