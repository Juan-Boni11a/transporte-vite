package com.example.transportsapi.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="vehicles")
@ToString
@EqualsAndHashCode
public class VehicleModel {


    @Id
    @Getter @Setter
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter @Column(name="plate")
    private String plate;


    @Getter @Setter @Column(name="brand")
    private String brand;


    @Getter @Setter @Column(name="model")
    private String model;


    @Getter @Setter @Column(name="color")
    private String color;


    @Getter @Setter @Column(name="engine")
    private String engine;


    @Getter @Setter @Column(name="enrollment")
    private String enrollment;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VehicleModel.Status status;

    public enum Status {
        USO
    }


}
