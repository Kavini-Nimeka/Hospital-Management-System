package org.slit.slitp2.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 12:55 AM
 */

@Entity
@Table(name = "appointment")
@Setter
@Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "patient",
            referencedColumnName = "id"
    )
    private Patient patient;
    @Column(
            name = "status"
    )
    private String status;
    @Column(
            name = "reserved_time"
    )
    private LocalDateTime reservedTime;

    @ManyToOne
    @JoinColumn(name = "doctor",
            referencedColumnName = "id")
    private Doctor doctor;
    @Column(
            name = "amount"
    )
    private Double amount;

    @OneToMany(
            mappedBy = "appointment",
            cascade = {CascadeType.ALL}
    )
    private List<Prescription> prescriptions;
}
