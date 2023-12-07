package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name="transportation_requests")
@ToString
@EqualsAndHashCode
public class TransportationRequestModel {


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
    @Column(name = "departure_date")
    private LocalDate departureDate;


    @Getter
    @Setter
    @Column(name = "departure_hour")
    private LocalTime departureHour;


    @Getter
    @Setter
    @Column(name = "return_date")
    private LocalDate returnDate;


    @Getter
    @Setter
    @Column(name = "return_hour")
    private LocalTime returnHour;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "officer_id")
    private UserModel officer;

    @Getter
    @Setter
    @Column(name = "companions_number")
    private Integer companionsNumber;


    @Getter
    @Setter
    @Column(name = "destiny")
    private String destiny;


    @Getter
    @Setter
    @Column(name = "aprox_duration")
    private String aproxDuration;

    @Getter
    @Setter
    @Column(name = "activity")
    private String activity;

    @Getter
    @Setter
    @Column(name = "all_time")
    private Boolean allTime;

}
