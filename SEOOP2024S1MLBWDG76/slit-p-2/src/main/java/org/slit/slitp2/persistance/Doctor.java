package org.slit.slitp2.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 12:40 AM
 */

@Entity
@Table(name = "doctor")
@Setter
@Getter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = "name"
    )
    private String name;
    @Column(
            name = "special"
    )
    private String special;
    @Column(
            name = "degree"
    )
    private String degree;
    @Column(
            name = "price"
    )
    private Double price;
    @OneToMany(
            mappedBy = "doctor",
            cascade = {CascadeType.ALL}
    )
    private List<Appointment> appointments;

}
