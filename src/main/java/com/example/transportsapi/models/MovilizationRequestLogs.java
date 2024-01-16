package com.example.transportsapi.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "movilization_request_logs")
@ToString
@EqualsAndHashCode
public class MovilizationRequestLogs {

    @Id
    @Getter @Setter @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "movilization_request_id")
    private MovilizationRequestModel movilizationRequest;

    @Getter @Setter
    @Column(name="content", length = 4000)
    private String content;


    @Getter @Setter
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;


    @Getter @Setter
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;


}
