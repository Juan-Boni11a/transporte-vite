package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="movilization_requests")
@ToString
@EqualsAndHashCode
public class MovilizationRequestModel {

    @Id
    @Getter @Setter @Column(name="id")
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
    @ManyToOne
    @JoinColumn(name = "movilization_type_id")
    private MovilizationTypes movilizationType;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "to_id")
    private MovilizationTo to;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "validity_id")
    private MovilizationValidities validity;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UserModel driver;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleModel vehicle;

    @Getter
    @Setter
    @Column(name = "emit_place")
    private String emitPlace;

    @Getter
    @Setter
    @Column(name = "emit_date")
    private LocalDate emitDate;

    @Getter
    @Setter
    @Column(name = "emit_hour")
    private LocalTime emitHour;


    @Getter
    @Setter
    @Column(name = "expiry_place")
    private String expiryPlace;

    @Getter
    @Setter
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Getter
    @Setter
    @Column(name = "expiry_hour")
    private LocalTime expiryHour;


    @Getter
    @Setter
    @Column(name="comments")
    private String comments;

}
