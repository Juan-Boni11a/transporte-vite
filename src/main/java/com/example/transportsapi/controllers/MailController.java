package com.example.transportsapi.controllers;


import com.example.transportsapi.service.MailService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/mails")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping
    public String sendTest() throws IOException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello friend", font);

        document.add(chunk);
        document.close();
        return mailService.sendDynamic(byteArrayOutputStream.toByteArray(), "pablotrujilloelo@gmail.com");
    }

}
