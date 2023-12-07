package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="service_stations")
@ToString
@EqualsAndHashCode
public class ServiceStationModel {

    @Id
    @Getter
    @Setter
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name="name")
    private String name;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityModel city;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ServiceStationModel.Type type;

    public enum Type {
        SUPPLY,
        MAINTENANCE
    }

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private ServiceStationModel.FuelType fuelType;

    public enum FuelType {
        GASOLINA
    }

    @Getter
    @Setter
    @Column(name = "activities")
    private String activities;

}
