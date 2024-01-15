package com.example.transportsapi.service;


import com.example.transportsapi.mappers.MovilizationRequestMapper;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MovilizationRequestService {

    @Autowired
    private MovilizationRequestRepository movilizationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private MovilizationToRepository movilizationToRepository;

    @Autowired
    private MovilizationValiditiesRepository movilizationValiditiesRepository;

    @Autowired
    private MovilizationTypeRepository movilizationTypeRepository;

    @Autowired
    private MailService mailService;


    @Autowired
    private MovilizationRequestMapper movilizationRequestMapper;

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

        MovilizationRequestModel movilizationRequestFound = movilizationRequestRepository.findById(movilizationRequestModel.getId()).orElse(null);

        if(movilizationRequestFound!=null){
            movilizationRequestFound.setCode("M-" + String.format("%03d", movilizationRequestFound.getId()));
            movilizationRequestRepository.save(movilizationRequestFound);
        }

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
        String requesterName = movilizationRequestModel.getRequester().getName() + " " + movilizationRequestModel.getRequester().getLastname();
        table.addCell(requesterName);

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


        List<UserModel> admins = userRepository.findByRoleId(1L);


        for (UserModel admin: admins) {
            mailService.sendDynamic(byteArrayOutputStream.toByteArray(), admin.getEmail());
        }

        return movilizationRequestModel;
    }

    public MovilizationRequestModel updateMovilizationRequest(MovilizationRequestModel movilizationRequest) throws IOException {

        MovilizationRequestModel movilizationRequestFound = movilizationRequestRepository.findById(movilizationRequest.getId()).orElse(null);

        movilizationRequestFound.setInitiatorId(userRepository.findById(movilizationRequest.getInitiatorId().getId()).orElse(null));
        movilizationRequestFound.setCurrentActivity(movilizationRequest.getCurrentActivity());
        movilizationRequestFound.setCurrentResponsible(userRepository.findById(movilizationRequest.getCurrentResponsible().getId()).orElse(null));
        movilizationRequestFound.setTo(movilizationToRepository.findById(movilizationRequest.getTo().getId()).orElse(null));
        movilizationRequestFound.setValidity( movilizationValiditiesRepository.findById(movilizationRequest.getValidity().getId()).orElse(null));
        movilizationRequestFound.setDriver(userRepository.findById(movilizationRequest.getDriver().getId()).orElse(null));
        movilizationRequestFound.setVehicle(vehiclesRepository.findById(movilizationRequest.getVehicle().getId()).orElse(null));
        movilizationRequestFound.setMovilizationType(  movilizationTypeRepository.findById(movilizationRequest.getMovilizationType().getId()).orElse(null));
        movilizationRequestFound.setEmitPlace(movilizationRequest.getEmitPlace());
        movilizationRequestFound.setEmitDate(movilizationRequest.getEmitDate());
        movilizationRequestFound.setEmitHour(movilizationRequest.getEmitHour());
        movilizationRequestFound.setExpiryPlace(movilizationRequest.getExpiryPlace());
        movilizationRequestFound.setExpiryDate(movilizationRequest.getExpiryDate());
        movilizationRequestFound.setExpiryHour(movilizationRequest.getExpiryHour());


        System.out.println(movilizationRequestFound.toString());



        movilizationRequestRepository.save(movilizationRequestFound);

        ByteArrayOutputStream byteArrayOutputStreamAdmin = new ByteArrayOutputStream();

        ByteArrayOutputStream byteArrayOutputStreamCustomer = new ByteArrayOutputStream();

        Document documentAdmin = new Document();
        Document documentCustomer = new Document();


        try {
            PdfWriter.getInstance(documentAdmin, byteArrayOutputStreamAdmin);
            PdfWriter.getInstance(documentCustomer, byteArrayOutputStreamCustomer);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        documentAdmin.open();
        documentCustomer.open();

        Font subtitleFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Font greetingFont = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);


        Paragraph greetingAdmin = new Paragraph();
        greetingAdmin.setFont(greetingFont);
        greetingAdmin.add("ORDEN DE MOVILIZACIÓN");
        greetingAdmin.setSpacingAfter(12);



        Paragraph greeting = new Paragraph();
        greeting.setFont(greetingFont);
        greeting.add("Tu solicitud de movilización ha sido aprobada, a continuación un resumen de la solicitud de movilización: ");
        greeting.setSpacingAfter(12);


        PdfPTable table = new PdfPTable(2);

        table.addCell("Nombre");
        String requesterName = movilizationRequestFound.getRequester().getName() + " " + movilizationRequestFound.getRequester().getLastname();
        table.addCell(requesterName);

        table.addCell("Actividad actual");
        table.addCell(movilizationRequestFound.getCurrentActivity());


        table.addCell("Responsable actual");

        String currentResponName = movilizationRequestFound.getCurrentResponsible().getName() + " " + movilizationRequestFound.getCurrentResponsible().getLastname();
        table.addCell(currentResponName);

        table.addCell("Tipo de movilización");
        table.addCell(movilizationRequestFound.getMovilizationType().getName());


        table.addCell("Para");
        table.addCell(movilizationRequestFound.getTo().getName());

        table.addCell("Vigente de");
        table.addCell(movilizationRequestFound.getValidity().getName());


        table.addCell("Conductor");
        String driverName = movilizationRequestFound.getDriver().getName() + " " + movilizationRequestFound.getDriver().getLastname();
        table.addCell(driverName);


        PdfPTable vehicleTable = new PdfPTable(2);

        Paragraph vehicleTitle = new Paragraph();
        vehicleTitle.setFont(subtitleFont);
        vehicleTitle.add("Datos del vehículo");
        vehicleTitle.setSpacingAfter(12);

        vehicleTable.addCell("Placa");
        vehicleTable.addCell(movilizationRequestFound.getVehicle().getPlate());


        vehicleTable.addCell("Marca");
        vehicleTable.addCell(movilizationRequestFound.getVehicle().getBrand());

        vehicleTable.addCell("Modelo");
        vehicleTable.addCell(movilizationRequestFound.getVehicle().getModel());

        vehicleTable.addCell("Color");
        vehicleTable.addCell(movilizationRequestFound.getVehicle().getColor());

        vehicleTable.addCell("Motor");
        vehicleTable.addCell(movilizationRequestFound.getVehicle().getEngine());

        vehicleTable.addCell("No. Matrícula");
        vehicleTable.addCell(movilizationRequestFound.getVehicle().getEnrollment());



        PdfPTable emitTable = new PdfPTable(2);

        Paragraph emitData = new Paragraph();
        emitData.setFont(subtitleFont);
        emitData.add("Datos de emisión");
        emitData.setSpacingAfter(12);

        emitTable.addCell("Lugar");
        emitTable.addCell(movilizationRequestFound.getEmitPlace());

        emitTable.addCell("Fecha");
        emitTable.addCell(String.valueOf(movilizationRequestFound.getEmitDate()));

        emitTable.addCell("Hora");
        emitTable.addCell(String.valueOf(movilizationRequestFound.getEmitHour()));


        PdfPTable expiryTable = new PdfPTable(2);

        Paragraph expiryData = new Paragraph();
        expiryData.setFont(subtitleFont);
        expiryData.add("Datos de caducidad");
        expiryData.setSpacingAfter(12);


        expiryTable.addCell("Lugar");
        expiryTable.addCell(movilizationRequestFound.getExpiryPlace());

        expiryTable.addCell("Fecha");
        expiryTable.addCell(String.valueOf(movilizationRequestFound.getExpiryDate()));

        expiryTable.addCell("Hora");
        expiryTable.addCell(String.valueOf(movilizationRequestFound.getExpiryHour()));


        PdfPTable signTable = new PdfPTable(2);


        signTable.setSpacingBefore(125);
        signTable.addCell(" ");
        signTable.addCell(" ");

        signTable.addCell("Firma conductor");
        signTable.addCell("Firma Supervisor");




        try {
            documentCustomer.add(greeting);
            documentCustomer.add(table);
            documentCustomer.add(vehicleTitle);
            documentCustomer.add(vehicleTable);
            documentCustomer.add(emitData);
            documentCustomer.add(emitTable);
            documentCustomer.add(expiryData);
            documentCustomer.add(expiryTable);

            documentAdmin.add(greetingAdmin);
            documentAdmin.add(table);
            documentAdmin.add(vehicleTitle);
            documentAdmin.add(vehicleTable);
            documentAdmin.add(emitData);
            documentAdmin.add(emitTable);
            documentAdmin.add(expiryData);
            documentAdmin.add(expiryTable);
            documentAdmin.add(signTable);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        documentCustomer.close();
        documentAdmin.close();



        mailService.sendDynamic(byteArrayOutputStreamCustomer.toByteArray(), movilizationRequestFound.getRequester().getEmail());


        List<UserModel> admins = userRepository.findByRoleId(1L);

        for (UserModel admin: admins) {
            mailService.sendDynamic(byteArrayOutputStreamAdmin.toByteArray(), admin.getEmail());
        }

        return movilizationRequestFound;
    }

    public void deleteMovilizationRequest(Long id) {
        movilizationRequestRepository.deleteById(id);
    }
}
