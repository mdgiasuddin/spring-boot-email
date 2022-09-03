package com.example.demo.services;

import com.example.demo.dtos.EmailDetails;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithFileDirectory(EmailDetails details);

    String sendMailWithGeneratedFile(EmailDetails details) throws DocumentException, IOException;

    String sendMailWithUploadedFile(final MultipartFile file) throws IOException, DocumentException;
}
