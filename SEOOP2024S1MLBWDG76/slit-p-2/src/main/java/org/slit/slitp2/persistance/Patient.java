package org.slit.slitp2.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 12:48 AM
 */


@Entity
@Table(name = "patient")
@Setter
@Getter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = "name"
    )
    private String name;
    @Column(
            name = "phone"
    )
    private String phone;
    @Column(
            name = "dob"
    )
    private LocalDate dob;
    @Column(
            name = "address"
    )
    private String address;
    @Column(
            name = "password"
    )
    private String password;
    @Column(
            name = "username"
    )
    private String username;
    @OneToMany(
            mappedBy = "patient",
            cascade = {CascadeType.ALL}
    )
    private List<Appointment> appointments;
}
