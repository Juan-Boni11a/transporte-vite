package com.example.transportsapi.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name="mechanical_exit_logs")
@ToString
@EqualsAndHashCode
public class MechanicalExitLogModel {
    @Id
    @Getter
    @Setter
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "responsible")
    private MechanicalExitLogModel.ResponsibleTypes responsible;

    public enum ResponsibleTypes {
        JEFE_TRANSPORTE,
        ASISTENTE,
        RESPONSABLE_ESTACION
    }

    @Getter
    @Setter
    @Column(name = "exit_date")
    private LocalDate exitDate;

}
