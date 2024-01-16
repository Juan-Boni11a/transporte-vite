package com.example.transportsapi.service;


import com.example.transportsapi.mappers.MovilizationRequestMapper;
import com.example.transportsapi.models.MovilizationRequestLogs;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovilizationRequestService {

    @Autowired
    private MovilizationRequestRepository movilizationRequestRepository;

    @Autowired
    private MovilizationRequestLogsRepository movilizationRequestLogsRepository;


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



        PdfPTable table = new PdfPTable(2);


        table.addCell("Identificador");
        table.addCell(movilizationRequestFound.getCode());

        table.addCell("Nombre");
        Optional<UserModel> requester =  userRepository.findById(movilizationRequestFound.getRequester().getId());
        String requesterName = requester.get().getName() + " " + requester.get().getLastname();
        table.addCell(requesterName);

        table.addCell("Fecha de salida");
        table.addCell(String.valueOf(movilizationRequestFound.getEmitDate()));


        table.addCell("Hora de salida");
        table.addCell(String.valueOf(movilizationRequestFound.getEmitHour()));

        String space = ",";

        table.addCell("Punto de salida");
        String departurePoint = String.valueOf(movilizationRequestFound.getEmitPlace());
        table.addCell(departurePoint);


        table.addCell("Punto de llegada");
        String arrivalPoint = String.valueOf(movilizationRequestFound.getExpiryPlace());
        table.addCell(arrivalPoint);




        try {
            document.add(titleChunk);
            document.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();


        List<UserModel> admins = userRepository.findByRoleId(1L);


        for (UserModel admin: admins) {
            mailService.sendDynamic(byteArrayOutputStream.toByteArray(), admin.getEmail(), "Nueva solicitud de movilización", "Ha ingresado una nueva solicitud de movilización, en el archivo adjunto están los detalles!");
        }

        MovilizationRequestLogs movilizationRequestLog = new MovilizationRequestLogs();
        movilizationRequestLog.setMovilizationRequest(movilizationRequestFound);
        movilizationRequestLog.setContent("Solicitud creada por: " +  requesterName);
        movilizationRequestLogsRepository.save(movilizationRequestLog);

        return movilizationRequestModel;
    }

    public MovilizationRequestModel updateMovilizationRequest(MovilizationRequestModel movilizationRequest) throws IOException {

        MovilizationRequestModel movilizationRequestFound = movilizationRequestRepository.findById(movilizationRequest.getId()).orElse(null);

        List<String> logs = new ArrayList<>();

        if(movilizationRequest.getStatus().name().equals("REJECTED")){


            if(!movilizationRequestFound.getStatus().name().equals(movilizationRequest.getStatus().name())){
                String oldValue = movilizationRequestFound.getStatus().name();
                String newValue = movilizationRequest.getStatus().name();
                System.out.println("El estado cambió de " + oldValue + " a " + newValue);
                logs.add("El estado cambió de " + oldValue + " a " + newValue);
            }

            movilizationRequestFound.setStatus(movilizationRequest.getStatus());
            movilizationRequestRepository.save(movilizationRequestFound);
            String customerEmailSubject = "Solicitud de movilización rechazada";
            String customerEmailContent = "Lamentamos informar que tu solicitud ha sido rechazada.";
            mailService.sendDynamicWithoutAtt(movilizationRequestFound.getRequester().getEmail(), customerEmailSubject, customerEmailContent);

            return movilizationRequestFound;
        }


        if(movilizationRequestFound.getCurrentActivity()!= movilizationRequest.getCurrentActivity()){
            String oldValue = movilizationRequestFound.getCurrentActivity();
            String newValue = movilizationRequest.getCurrentActivity();

            System.out.println("Actividad actual cambió de " + oldValue + " a " + newValue);

            logs.add("Actividad actual cambió de " + oldValue + " a " + newValue);

            /*
            MovilizationRequestLogs movilizationRequestLog = new MovilizationRequestLogs();
            movilizationRequestLog.setMovilizationRequest(movilizationRequestFound);
            movilizationRequestLog.setContent("Actividad actual cambió de " + oldValue + " a " + newValue);
            movilizationRequestLogsRepository.save(movilizationRequestLog);
             */
        }


        UserModel newCurrentResponsible = userRepository.findById(movilizationRequest.getCurrentResponsible().getId()).orElse(null);

        if (movilizationRequestFound.getCurrentResponsible()==null){
            String newValue = newCurrentResponsible.getName() + " " + newCurrentResponsible.getLastname();
            logs.add("Responsable actual cambió a " + newValue);
        }else{
            UserModel oldCurrentResponsible = userRepository.findById(movilizationRequestFound.getCurrentResponsible().getId()).orElse(null);
            if(oldCurrentResponsible.getId() != newCurrentResponsible.getId()){
                String oldValue = oldCurrentResponsible.getName() + " " + oldCurrentResponsible.getLastname();
                String newValue = newCurrentResponsible.getName() + " " + newCurrentResponsible.getLastname();
                System.out.println("Responsable actual cambió de " + oldValue + " a " + newValue);
                logs.add("Responsable actual cambió de " + oldValue + " a " + newValue);
            }
        }



        UserModel newDriver = userRepository.findById(movilizationRequest.getDriver().getId()).orElse(null);

        if(movilizationRequestFound.getDriver()==null){
            String newValue = newDriver.getName() + " " + newDriver.getLastname();
            System.out.println("Conductor cambió a " + newValue);
            logs.add("Conductor cambió a " + newValue);
        }else{
            UserModel oldDriver = userRepository.findById(movilizationRequestFound.getDriver().getId()).orElse(null);
            if(oldDriver.getId() != newDriver.getId()){
                String oldValue = oldDriver.getName() + " " + oldDriver.getLastname();
                String newValue = newDriver.getName() + " " + newDriver.getLastname();
                System.out.println("Conductor cambió de " + oldValue + " a " + newValue);
                logs.add("Conductor cambió de " + oldValue + " a " + newValue);
            }
        }


        VehicleModel newVehicle = vehiclesRepository.findById(movilizationRequest.getVehicle().getId()).orElse(null);

        if(movilizationRequestFound.getVehicle()==null){
            String newValue = newVehicle.getPlate();
            System.out.println("El vehículo cambió a " + newValue);
            logs.add("El vehículo cambió a " + newValue);
        }else{
            VehicleModel oldVehicle = vehiclesRepository.findById(movilizationRequestFound.getVehicle().getId()).orElse(null);
            if(oldVehicle.getId() != newVehicle.getId()){
                String oldValue = oldVehicle.getPlate();
                String newValue = newVehicle.getPlate();
                System.out.println("El vehículo cambió de " + oldValue + " a " + newValue);
                logs.add("El vehículo cambió de " + oldValue + " a " + newValue);
            }
        }


        if(!movilizationRequestFound.getStatus().name().equals(movilizationRequest.getStatus().name())){
            String oldValue = movilizationRequestFound.getStatus().name();
            String newValue = movilizationRequest.getStatus().name();
            System.out.println("El estado cambió de " + oldValue + " a " + newValue);
            logs.add("El estado cambió de " + oldValue + " a " + newValue);
        }

        movilizationRequestFound.setCurrentActivity(movilizationRequest.getCurrentActivity());
        movilizationRequestFound.setCurrentResponsible(newCurrentResponsible);
        movilizationRequestFound.setDriver(userRepository.findById(movilizationRequest.getDriver().getId()).orElse(null));
        movilizationRequestFound.setVehicle(vehiclesRepository.findById(movilizationRequest.getVehicle().getId()).orElse(null));
        movilizationRequestFound.setStatus(movilizationRequest.getStatus());

        movilizationRequestRepository.save(movilizationRequestFound);

        for (String log : logs) {
            System.out.println(log);
            MovilizationRequestLogs movilizationRequestLog = new MovilizationRequestLogs();
            movilizationRequestLog.setMovilizationRequest(movilizationRequestFound);
            movilizationRequestLog.setContent(log);
            movilizationRequestLogsRepository.save(movilizationRequestLog);
        }

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





        PdfPTable table = new PdfPTable(2);


        table.addCell("Identificador");
        table.addCell(movilizationRequestFound.getCode());

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


        String customerEmailSubject = "Solicitud de movilización aprobada";
        String customerEmailContent = "Nos complace informar que tu solicitud ha sido aprobada, en el adjunto están los detalles!";
        mailService.sendDynamic(byteArrayOutputStreamCustomer.toByteArray(), movilizationRequestFound.getRequester().getEmail(), customerEmailSubject, customerEmailContent);


        List<UserModel> admins = userRepository.findByRoleId(1L);

        for (UserModel admin: admins) {

            mailService.sendDynamic(byteArrayOutputStreamAdmin.toByteArray(), admin.getEmail(), "Orden de movilización", "Se ha generado una nueva orden de movilización, en el adjunto están los detalles:");
        }

        return movilizationRequestFound;
    }

    public void deleteMovilizationRequest(Long id) {
        movilizationRequestRepository.deleteById(id);
    }
}
