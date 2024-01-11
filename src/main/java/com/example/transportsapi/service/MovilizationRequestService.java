package com.example.transportsapi.service;


import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.repository.MovilizationRequestRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class MovilizationRequestService {

    @Autowired
    private MovilizationRequestRepository movilizationRequestRepository;

    @Autowired
    private MailService mailService;


    public List<MovilizationRequestModel> getAllMovilizationRequests() {
        return movilizationRequestRepository.findAll();
    }

    public List<MovilizationRequestModel> getMovilizationRequestsByRequester(Long id) {
        MovilizationRequestModel movilizationRequest = new MovilizationRequestModel();
        UserModel user = new UserModel();
        user.setId(id);
        movilizationRequest.setRequester(user);
        Example<MovilizationRequestModel> example = Example.of(movilizationRequest);
        List<MovilizationRequestModel> results = movilizationRequestRepository.findAll(example);
        return results;
    }

    public MovilizationRequestModel createMovilizationRequest(MovilizationRequestModel movilizationRequest) throws DocumentException, IOException {

        MovilizationRequestModel movilizationRequestModel = movilizationRequestRepository.save(movilizationRequest);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        Font greetingFont = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        Font noteFont = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);

        Paragraph titleChunk = new Paragraph();
        titleChunk.setFont(titleFont);
        titleChunk.add("Solicitud de Movilización");
        titleChunk.setSpacingAfter(20);

        Paragraph greeting = new Paragraph();
        greeting.setFont(greetingFont);
        greeting.add("Hola administrador! un cliente ha realizado una solicitud de movilización, te dejamos los detalles a continuación: ");
        greeting.setSpacingAfter(12);


        PdfPTable table = new PdfPTable(2);

        table.addCell("Nombre");
        table.addCell("Jake Danger");

        table.addCell("Fecha de salida");
        table.addCell(String.valueOf(movilizationRequestModel.getDateArrival()));


        table.addCell("Hora de salida");
        table.addCell(String.valueOf(movilizationRequestModel.getHourArrival()));

        String space = ",";

        table.addCell("Punto de salida");
        String departurePoint = String.valueOf(movilizationRequestModel.getLatDeparture()) + space + String.valueOf(movilizationRequestModel.getLongDeparture()) ;
        table.addCell(departurePoint);


        table.addCell("Punto de llegada");
        String arrivalPoint = String.valueOf(movilizationRequestModel.getLatArrival()) + space + String.valueOf(movilizationRequestModel.getLongArrival());
        table.addCell(arrivalPoint);


        Chunk noteChunk = new Chunk("Nota: En Google Maps, ingresar las coordenadas indicadas en los puntos de salida y llegada ", noteFont);


        try {
            document.add(titleChunk);
            document.add(greeting);
            document.add(table);
            document.add(noteChunk);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();

        mailService.sendDynamic(byteArrayOutputStream.toByteArray());


        return movilizationRequestRepository.save(movilizationRequest);
    }

    public MovilizationRequestModel updateMovilizationRequest(MovilizationRequestModel movilizationRequest) {
        return movilizationRequestRepository.save(movilizationRequest);
    }

    public void deleteMovilizationRequest(Long id) {
        movilizationRequestRepository.deleteById(id);
    }
}
