package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name="supply_requests")
@ToString
@EqualsAndHashCode
public class SupplyRequestModel {


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
    @Column(name = "supply_date")
    private LocalDate departureDate;


    @Getter
    @Setter
    @Column(name = "supply_hour")
    private LocalTime departureHour;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UserModel driver;


    @Getter
    @Setter
    @Column(name = "current_km")
    private Integer currentKm;


    @Getter
    @Setter
    @Column(name = "verified_movilization_data")
    private Boolean verifiedMovilizationData;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "service_station_id")
    private ServiceStationModel serviceStation;


    @Getter
    @Setter
    @Column(name = "fuel")
    private String fuel;


    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    public enum FuelType {
        SUPER,
        EXTRA
    }

    @Getter
    @Setter
    @Column(name = "price")
    private Double price;

    @Getter
    @Setter
    @Column(name = "gallons")
    private Double gallons;

    @Getter
    @Setter
    @Column(name = "total")
    private Double total;

    @Getter
    @Setter
    @Column(name = "barCode")
    private String barCode;

}
