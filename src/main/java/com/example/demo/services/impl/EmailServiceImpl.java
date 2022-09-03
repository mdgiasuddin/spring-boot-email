package com.example.demo.services.impl;

import com.example.demo.dtos.EmailDetails;
import com.example.demo.services.EmailService;
import com.example.demo.services.PdfService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final PdfService pdfService;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(EmailDetails details) {

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setSubject(details.getSubject());
            mailMessage.setText(details.getMessageBody());

            // Sending the mail
            javaMailSender.send(mailMessage);

            return "Mail Sent Successfully...";

        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    private String sendMailWithAttachment(EmailDetails details, String filename, Resource resource) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to be sent.
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMessageBody(), false);
            mimeMessageHelper.setSubject(details.getSubject());

            mimeMessageHelper.addAttachment(filename, resource);

            // Sending the mail
            javaMailSender.send(mimeMessage);

            return "Mail sent Successfully";

        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }

    @Override
    public String sendMailWithFileDirectory(EmailDetails details) {
        FileSystemResource resource = new FileSystemResource(new File(details.getAttachment()));

        return sendMailWithAttachment(details, Objects.requireNonNull(resource.getFilename()), resource);
    }

    @Override
    public String sendMailWithGeneratedFile(EmailDetails details) throws DocumentException {
        ByteArrayOutputStream outputStream = pdfService.createPdf();

        return sendMailWithAttachment(details, "statement.pdf", new ByteArrayResource(outputStream.toByteArray()));
    }

    @Override
    public String sendMailWithUploadedFile(final MultipartFile file) throws IOException {

        EmailDetails details = EmailDetails.builder()
                .recipient("mdgiashu069@gmail.com")
                .subject("Email with Uploaded Attachment")
                .messageBody("Dear Giash.\nPlease the uploaded attachment carefully. Please let me know as soon as possible if any mismatch found.")
                .build();

        return sendMailWithAttachment(details, file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
    }
}
