package com.example.transportsapi.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name="maintenance_requests")
@ToString
@EqualsAndHashCode
public class MaintenanceRequestModel {

    @Id
    @Getter
    @Setter
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private UserModel initiatorId;

    @Getter
    @Setter
    @Column(name="current_activity")
    private String currentActivity;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "current_responsible_id")
    private UserModel currentResponsible;

    @Getter
    @Setter
    @Column(name = "date")
    private LocalDate date;


    @Getter
    @Setter
    @Column(name = "hour")
    private LocalTime hour;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleModel vehicle;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UserModel driver;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "service_station_id")
    private ServiceStationModel serviceStation;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "work_type")
    private MaintenanceRequestModel.WorkType workType;

    public enum WorkType {
        PREVENTIVO,
        CORRECTIVO
    }

    @Getter
    @Setter
    @Column(name = "barCode")
    private String barCode;


    @Getter @Setter
    @ManyToMany(
            fetch= FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "maintenance_request_activities",
            joinColumns = @JoinColumn(name = "maintenance_request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id")
    )
    private Set<ActivityModel> activities;



}
