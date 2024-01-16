package com.example.transportsapi.service;

import com.example.transportsapi.models.*;
import com.example.transportsapi.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.lang.Long;
import java.util.Optional;

@Service
public class MaintenanceRequestService {
    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;


    @Autowired
    private ActivityRepository activityRepository;


    @Autowired
    private ServiceStationRepository serviceStationRepository;

    @Autowired
    private VehiclesRepository vehiclesRepository;


    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    public List<MaintenanceRequestModel> getAll() {
        return maintenanceRequestRepository.findAll();
    }

    public MaintenanceRequestModel createOrUpdate(MaintenanceRequestModel maintenanceRequest) throws IOException {
        MaintenanceRequestModel maintenanceRequestCreated = maintenanceRequestRepository.save(maintenanceRequest);

        MaintenanceRequestModel maintenanceRequestFound = maintenanceRequestRepository.findById(maintenanceRequestCreated.getId()).orElse(null);

        if(maintenanceRequestFound!=null){
            maintenanceRequestFound.setCode("M-" + String.format("%03d", maintenanceRequestFound.getId()));
            maintenanceRequestRepository.save(maintenanceRequestFound);
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
        titleChunk.add("Solicitud de Mantenimiento");
        titleChunk.setSpacingAfter(20);


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


        Paragraph greetingAdmin = new Paragraph();
        greetingAdmin.setFont(greetingFont);
        greetingAdmin.add("ORDEN DE MANTENIMIENTO");
        greetingAdmin.setSpacingAfter(12);


        PdfPTable table = new PdfPTable(2);


        table.addCell("Estado");
        table.addCell(maintenanceRequestFound.getStatus().name());

        table.addCell("Identificador");
        table.addCell(maintenanceRequestFound.getCode());



        Optional<UserModel> requester = userRepository.findById(maintenanceRequestFound.getRequester().getId());

        table.addCell("Nombre");
        String requesterName = requester.get().getName() + " " + requester.get().getLastname();
        table.addCell(requesterName);

        table.addCell("Actividad actual");
        table.addCell(maintenanceRequestFound.getCurrentActivity());



        Optional<UserModel> responsible = userRepository.findById(maintenanceRequestFound.getCurrentResponsible().getId());


        table.addCell("Responsable actual");
        String currentResponName = responsible.get().getName() + " " + responsible.get().getLastname();
        table.addCell(currentResponName);


        Optional<UserModel> driver = userRepository.findById(maintenanceRequestFound.getDriver().getId());
        table.addCell("Conductor");
        String currentDriverName = driver.get().getName() + " " + driver.get().getLastname();
        table.addCell(currentDriverName);


        table.addCell("Fecha");
        table.addCell(String.valueOf(maintenanceRequestFound.getDate()));


        table.addCell("Hora");
        table.addCell(String.valueOf(maintenanceRequestFound.getHour()));


        table.addCell("Tipo de trabajo");
        table.addCell(maintenanceRequestFound.getWorkType().name());



        Optional<ServiceStationModel> serviceStation = serviceStationRepository.findById(maintenanceRequestFound.getServiceStation().getId());

        table.addCell("Estación de servicio");
        table.addCell(serviceStation.get().getName());


        PdfPTable vehicleTable = new PdfPTable(2);

        Paragraph vehicleTitle = new Paragraph();
        vehicleTitle.setFont(subtitleFont);
        vehicleTitle.add("Datos del vehículo");
        vehicleTitle.setSpacingAfter(12);



        Optional<VehicleModel> vehicle = vehiclesRepository.findById(maintenanceRequestFound.getVehicle().getId());

        vehicleTable.addCell("Placa");
        vehicleTable.addCell(vehicle.get().getPlate());


        vehicleTable.addCell("Marca");
        vehicleTable.addCell(vehicle.get().getBrand());

        vehicleTable.addCell("Modelo");
        vehicleTable.addCell(vehicle.get().getModel());

        vehicleTable.addCell("Color");
        vehicleTable.addCell(vehicle.get().getColor());

        vehicleTable.addCell("Motor");
        vehicleTable.addCell(vehicle.get().getEngine());

        vehicleTable.addCell("No. Matrícula");
        vehicleTable.addCell(vehicle.get().getEnrollment());


        PdfPTable signTable = new PdfPTable(2);


        signTable.setSpacingBefore(150);
        signTable.addCell(" ");
        signTable.addCell(" ");

        signTable.addCell("Firma conductor");
        signTable.addCell("Firma Supervisor");

        try {
            documentCustomer.add(table);
            documentCustomer.add(vehicleTitle);
            documentCustomer.add(vehicleTable);

            documentAdmin.add(greetingAdmin);
            documentAdmin.add(table);
            documentAdmin.add(vehicleTitle);
            documentAdmin.add(vehicleTable);
            documentAdmin.add(signTable);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        documentCustomer.close();
        documentAdmin.close();

        /*
        String customerEmailSubject = "Solicitud de mantenimiento actualizada";
        String customerEmailContent = "Se ha actualizado tu solicitud de mantenimiento";
        System.out.println(maintenanceRequestFound.getRequester().toString());
        mailService.sendDynamic(byteArrayOutputStreamCustomer.toByteArray(), requester.get().getEmail(), customerEmailSubject, customerEmailContent);
        */

        List<UserModel> admins = userRepository.findByRoleId(1L);

        for (UserModel admin: admins) {
            mailService.sendDynamic(byteArrayOutputStreamAdmin.toByteArray(), admin.getEmail(), "Orden de movilización", "Se ha actualizado una orden de mantenimiento, en el adjunto están los detalles:");
        }

        return maintenanceRequestFound;

    }

    public List<MaintenanceRequestModel> getMaintenanceRequestsByRequester(Long id) {
        System.out.println("AAAAAAAA");
        MaintenanceRequestModel maintenanceRequest = new MaintenanceRequestModel();
        UserModel user = new UserModel();
        user.setId(id);
        maintenanceRequest.setRequester(user);
        Example<MaintenanceRequestModel> example = Example.of(maintenanceRequest);
        List<MaintenanceRequestModel> results = maintenanceRequestRepository.findAll(example);
        return results;
    }


    public void delete(Long id) {
        maintenanceRequestRepository.deleteById(id);
    }


    @Transactional
    public MaintenanceRequestModel addActivityToMaintenance(Long maintenanceRequestId, Long activityId) {
        MaintenanceRequestModel maintenanceRequest = maintenanceRequestRepository.findById(maintenanceRequestId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        ActivityModel activity = activityRepository.findById(activityId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        maintenanceRequest.getActivities().add(activity);
        maintenanceRequestRepository.save(maintenanceRequest);

        return maintenanceRequest;
    }
}
