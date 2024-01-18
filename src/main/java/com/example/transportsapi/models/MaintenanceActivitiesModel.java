package com.example.transportsapi.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "maintenance_request_activities")
@ToString
@EqualsAndHashCode
public class MaintenanceActivitiesModel {


    @Id
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "maintenance_request_id")
    private MaintenanceRequestModel maintenanceRequest;


    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityModel activity;


}
