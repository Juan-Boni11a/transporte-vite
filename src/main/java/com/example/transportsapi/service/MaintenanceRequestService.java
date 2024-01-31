package com.example.transportsapi.service;

import com.example.transportsapi.models.*;
import com.example.transportsapi.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.lang.Long;
import java.util.List;

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

    @Autowired
    private MaintenanceRequestLogsRepository maintenanceRequestLogsRepository;

    public List<MaintenanceRequestModel> getAll() {
        return maintenanceRequestRepository.findAll();
    }

    public MaintenanceRequestModel create(MaintenanceRequestModel maintenanceRequest) throws IOException {
        MaintenanceRequestModel maintenanceRequestCreated = maintenanceRequestRepository.save(maintenanceRequest);

        MaintenanceRequestModel maintenanceRequestFound = maintenanceRequestRepository.findById(maintenanceRequestCreated.getId()).orElse(null);

        if(maintenanceRequestFound!=null){
            maintenanceRequestFound.setCode("MT-" + String.format("%03d", maintenanceRequestFound.getId()));
            maintenanceRequestRepository.save(maintenanceRequestFound);
        }

        Optional<UserModel> requester = userRepository.findById(maintenanceRequestFound.getRequester().getId());
        String requesterName = requester.get().getName() + " " + requester.get().getLastname();

        /*
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
        */

        /*
        String customerEmailSubject = "Solicitud de mantenimiento actualizada";
        String customerEmailContent = "Se ha actualizado tu solicitud de mantenimiento";
        System.out.println(maintenanceRequestFound.getRequester().toString());
        mailService.sendDynamic(byteArrayOutputStreamCustomer.toByteArray(), requester.get().getEmail(), customerEmailSubject, customerEmailContent);
        */

        /*
        List<UserModel> admins = userRepository.findByRoleId(1L);

        for (UserModel admin: admins) {
            mailService.sendDynamic(byteArrayOutputStreamAdmin.toByteArray(), admin.getEmail(), "Orden de movilización", "Se ha actualizado una orden de mantenimiento, en el adjunto están los detalles:");
        }
         */



        MaintenanceRequestLogs maintenanceRequestLog = new MaintenanceRequestLogs();
        maintenanceRequestLog.setMaintenanceRequest(maintenanceRequestFound);
        maintenanceRequestLog.setContent("Solicitud creada por: " +  requesterName);
        maintenanceRequestLogsRepository.save(maintenanceRequestLog);


        return maintenanceRequestFound;

    }


    public MaintenanceRequestModel update(MaintenanceRequestModel maintenanceRequest) throws IOException {

        MaintenanceRequestModel maintenanceRequestFound = maintenanceRequestRepository.findById(maintenanceRequest.getId()).orElse(null);

        System.out.println(maintenanceRequestFound.getCode());

        List<String> logs = new ArrayList<>();

        if (maintenanceRequest.getStatus().name().equals("RECHAZADA")) {

            if (!maintenanceRequestFound.getStatus().name().equals(maintenanceRequest.getStatus().name())) {
                String oldValue = maintenanceRequestFound.getStatus().name();
                String newValue = maintenanceRequest.getStatus().name();
                System.out.println("El estado cambió de " + oldValue + " a " + newValue);
                logs.add("El estado cambió de " + oldValue + " a " + newValue);
            }

            maintenanceRequestFound.setStatus(maintenanceRequest.getStatus());
            maintenanceRequestRepository.save(maintenanceRequestFound);
            String customerEmailSubject = "Solicitud de mantenimiento rechazada";
            String customerEmailContent = "Lamentamos informar que tu solicitud con identificador " + maintenanceRequestFound.getCode()  + " ha sido rechazada.";
            mailService.sendDynamicWithoutAtt(maintenanceRequestFound.getRequester().getEmail(), customerEmailSubject, customerEmailContent);
            return maintenanceRequestFound;
        }



        if (!Objects.equals(maintenanceRequestFound.getCurrentActivity(), maintenanceRequest.getCurrentActivity())) {
            String oldValue = maintenanceRequestFound.getCurrentActivity();
            String newValue = maintenanceRequest.getCurrentActivity();

            System.out.println("Actividad actual cambió de " + oldValue + " a " + newValue);

            logs.add("Actividad actual cambió de " + oldValue + " a " + newValue);

            /*
            MovilizationRequestLogs movilizationRequestLog = new MovilizationRequestLogs();
            movilizationRequestLog.setMovilizationRequest(movilizationRequestFound);
            movilizationRequestLog.setContent("Actividad actual cambió de " + oldValue + " a " + newValue);
            movilizationRequestLogsRepository.save(movilizationRequestLog);
             */
        }


        UserModel newCurrentResponsible = userRepository.findById(maintenanceRequest.getCurrentResponsible().getId()).orElse(null);

        if (maintenanceRequestFound.getCurrentResponsible() == null) {
            String newValue = newCurrentResponsible.getName() + " " + newCurrentResponsible.getLastname();
            logs.add("Responsable actual cambió a " + newValue);
        } else {
            UserModel oldCurrentResponsible = userRepository.findById(maintenanceRequestFound.getCurrentResponsible().getId()).orElse(null);
            if (oldCurrentResponsible.getId() != newCurrentResponsible.getId()) {
                String oldValue = oldCurrentResponsible.getName() + " " + oldCurrentResponsible.getLastname();
                String newValue = newCurrentResponsible.getName() + " " + newCurrentResponsible.getLastname();
                System.out.println("Responsable actual cambió de " + oldValue + " a " + newValue);
                logs.add("Responsable actual cambió de " + oldValue + " a " + newValue);
            }
        }


        UserModel newDriver = userRepository.findById(maintenanceRequest.getDriver().getId()).orElse(null);

        if (maintenanceRequestFound.getDriver() == null) {
            String newValue = newDriver.getName() + " " + newDriver.getLastname();
            System.out.println("Conductor cambió a " + newValue);
            logs.add("Conductor cambió a " + newValue);
        } else {
            UserModel oldDriver = userRepository.findById(maintenanceRequestFound.getDriver().getId()).orElse(null);
            if (oldDriver.getId() != newDriver.getId()) {
                String oldValue = oldDriver.getName() + " " + oldDriver.getLastname();
                String newValue = newDriver.getName() + " " + newDriver.getLastname();
                System.out.println("Conductor cambió de " + oldValue + " a " + newValue);
                logs.add("Conductor cambió de " + oldValue + " a " + newValue);
            }
        }


        VehicleModel newVehicle = vehiclesRepository.findById(maintenanceRequest.getVehicle().getId()).orElse(null);

        if (maintenanceRequestFound.getVehicle() == null) {
            String newValue = newVehicle.getPlate();
            System.out.println("El vehículo cambió a " + newValue);
            logs.add("El vehículo cambió a " + newValue);
        } else {
            VehicleModel oldVehicle = vehiclesRepository.findById(maintenanceRequestFound.getVehicle().getId()).orElse(null);
            if (oldVehicle.getId() != newVehicle.getId()) {
                String oldValue = oldVehicle.getPlate();
                String newValue = newVehicle.getPlate();
                System.out.println("El vehículo cambió de " + oldValue + " a " + newValue);
                logs.add("El vehículo cambió de " + oldValue + " a " + newValue);
            }
        }


        if (!maintenanceRequestFound.getStatus().name().equals(maintenanceRequest.getStatus().name())) {
            String oldValue = maintenanceRequestFound.getStatus().name();
            String newValue = maintenanceRequest.getStatus().name();
            System.out.println("El estado cambió de " + oldValue + " a " + newValue);
            logs.add("El estado cambió de " + oldValue + " a " + newValue);
        }

        maintenanceRequest.setCode(maintenanceRequestFound.getCode());
        maintenanceRequestRepository.save(maintenanceRequest);

        for (String log : logs) {
            System.out.println(log);
            MaintenanceRequestLogs maintenanceRequestLog = new MaintenanceRequestLogs();
            maintenanceRequestLog.setMaintenanceRequest(maintenanceRequestFound);
            maintenanceRequestLog.setContent(log);
            maintenanceRequestLogsRepository.save(maintenanceRequestLog);
        }

        /*

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

        */

        /*
        String customerEmailSubject = "Solicitud de mantenimiento actualizada";
        String customerEmailContent = "Se ha actualizado tu solicitud de mantenimiento";
        System.out.println(maintenanceRequestFound.getRequester().toString());
        mailService.sendDynamic(byteArrayOutputStreamCustomer.toByteArray(), requester.get().getEmail(), customerEmailSubject, customerEmailContent);
        */

        /*
        List<UserModel> admins = userRepository.findByRoleId(1L);

        for (UserModel admin: admins) {
            mailService.sendDynamic(byteArrayOutputStreamAdmin.toByteArray(), admin.getEmail(), "Orden de movilización", "Se ha actualizado una orden de mantenimiento, en el adjunto están los detalles:");
        }

         */

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


    public void buildPdf(Integer id) throws IOException {
        MaintenanceRequestModel maintenanceRequestFound = maintenanceRequestRepository.findById(Long.valueOf(id)).orElse(null);

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

        /*
        table.addCell("Estado");
        table.addCell(maintenanceRequestFound.getStatus().name());
        */

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




        PdfPTable driverTable = new PdfPTable(2);

        Paragraph driverTitle = new Paragraph();
        driverTitle.setFont(subtitleFont);
        driverTitle.add("Datos del conductor");
        driverTitle.setSpacingAfter(12);

        Optional<UserModel> driver = userRepository.findById(maintenanceRequestFound.getDriver().getId());

        driverTable.addCell("Nombre");
        String currentDriverName = driver.get().getName() + " " + driver.get().getLastname();
        driverTable.addCell(currentDriverName);


        driverTable.addCell("Cédula de identidad");
        driverTable.addCell(driver.get().getCi());

        driverTable.addCell("Número de teléfono");
        driverTable.addCell(driver.get().getPhone_number());

        driverTable.addCell("Correo electrónico");
        driverTable.addCell(driver.get().getEmail());

        String licenseType = driver.get().getLicenseType();
        if(licenseType != null){
            driverTable.addCell("Tipo de licencia");
            driverTable.addCell(licenseType);
        }

        String licenseExpiryDate = String.valueOf(driver.get().getLicenceExpiryDate());
        if(licenseExpiryDate != null){
            driverTable.addCell("Fecha de expiración de licencia");
            driverTable.addCell(licenseExpiryDate);
        }


        PdfPTable signTable = new PdfPTable(2);


        signTable.setSpacingBefore(150);
        signTable.addCell(" ");
        signTable.addCell(" ");

        signTable.addCell("Firma conductor");
        signTable.addCell("Firma Supervisor");




        Paragraph activitiesTitle = new Paragraph();
        activitiesTitle.setFont(subtitleFont);
        activitiesTitle.add("Actividades");
        activitiesTitle.setSpacingAfter(12);

        PdfPTable activitiesTable = new PdfPTable(1);


        List<ActivityModel> activities = activityRepository.findByMaintenanceRequest(maintenanceRequestFound.getId());
        System.out.println(activities.size());

        for (ActivityModel activity: activities) {
            System.out.println(activity.getName());
            PdfPCell activityNameCell = new PdfPCell(new Phrase(activity.getName()));
            activitiesTable.addCell(activityNameCell);
        }


        try {
            documentCustomer.add(table);
            documentCustomer.add(vehicleTitle);
            documentCustomer.add(vehicleTable);

            documentCustomer.add(driverTitle);
            documentCustomer.add(driverTable);

            documentCustomer.add(activitiesTitle);
            documentCustomer.add(activitiesTable);

            documentAdmin.add(greetingAdmin);
            documentAdmin.add(table);
            documentAdmin.add(vehicleTitle);
            documentAdmin.add(vehicleTable);

            documentAdmin.add(driverTitle);
            documentAdmin.add(driverTable);

            documentAdmin.add(activitiesTitle);
            documentAdmin.add(activitiesTable);
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
            mailService.sendDynamic(byteArrayOutputStreamAdmin.toByteArray(), admin.getEmail(), "Orden de mantenimiento", "Se ha actualizado una orden de mantenimiento, en el adjunto están los detalles:");
        }



    }


    @Transactional
    public MaintenanceRequestModel addActivityToMaintenance(Long maintenanceRequestId, Long activityId) throws IOException {
        MaintenanceRequestModel maintenanceRequest = maintenanceRequestRepository.findById(maintenanceRequestId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        ActivityModel activity = activityRepository.findById(activityId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        maintenanceRequest.getActivities().add(activity);
        maintenanceRequestRepository.save(maintenanceRequest);

        return maintenanceRequest;
    }
}
