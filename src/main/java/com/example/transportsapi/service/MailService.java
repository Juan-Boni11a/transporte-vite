package com.example.transportsapi.service;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    public String sendTextEmail() throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        Email from = new Email("managementdev90@gmail.com");
        String subject = "TEST";
        Email to = new Email("pablotrujilloelo@gmail.com");
        Content content = new Content("text/plain", "This is a test email");
        Mail mail = new Mail(from, subject, to, content);


        Attachments attachments = new Attachments();
        attachments.setContent("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gQ3JhcyBwdW12");
        attachments.setType("application/pdf");
        attachments.setFilename("balance_001.pdf");
        attachments.setDisposition("attachment");
        attachments.setContentId("Balance Sheet");
        mail.addAttachments(attachments);


        MailSettings mailSettings = new MailSettings();
        Setting sandBoxMode = new Setting();
        sandBoxMode.setEnable(true);
        mailSettings.setSandboxMode(sandBoxMode);


        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public String sendDynamic(byte[] pdfBytes) throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        Email from = new Email("managementdev90@gmail.com");
        Email to = new Email("pablotrujilloelo@gmail.com");

        Mail mail = new Mail();

        mail.setFrom(from);
        mail.setTemplateId("d-959ebf53a60d4531be1032a943eb9921");
        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("url", "");
        personalization.addTo(to);
        mail.addPersonalization(personalization);


        Attachments attachments3 = new Attachments();
        Base64 x = new Base64();
        String fileDataString = x.encodeAsString(pdfBytes);
        attachments3.setContent(fileDataString);
        attachments3.setType("application/pdf");
        attachments3.setFilename("resumen.pdf");
        attachments3.setDisposition("attachment");
        attachments3.setContentId("Banner");
        mail.addAttachments(attachments3);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }



}
