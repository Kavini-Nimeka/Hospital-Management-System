package org.slit.slitp2.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 09:36 PM
 */

@Entity
@Table(name = "prescription")
@Setter
@Getter
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "appointment", referencedColumnName = "id")
    private Appointment appointment;


    @OneToMany(
            mappedBy = "prescription",
            cascade = {CascadeType.ALL}
    )
    private List<PrescriptionItem> prescriptionItems;

}
