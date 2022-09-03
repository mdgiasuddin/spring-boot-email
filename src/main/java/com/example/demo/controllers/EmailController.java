package com.example.demo.controllers;

import com.example.demo.dtos.EmailDetails;
import com.example.demo.services.EmailService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/simple")
    public String sendMail(@RequestBody EmailDetails details) {
        return emailService.sendSimpleMail(details);
    }

    @PostMapping("/file/directory")
    public String sendMailWithFileDirectory(@RequestBody EmailDetails details) {
        return emailService.sendMailWithFileDirectory(details);
    }

    @PostMapping("/file/generated")
    public String sendMailWithGeneratedFile(@RequestBody EmailDetails details) throws DocumentException, IOException {
        return emailService.sendMailWithGeneratedFile(details);
    }

    @PostMapping("/file/upload")
    public String sendMailWithUploadedFile(@RequestParam("file") final MultipartFile file) throws DocumentException, IOException {
        return emailService.sendMailWithUploadedFile(file);
    }

}
