package org.slit.slitp2.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 09:34 PM
 */


@Entity
@Table(name = "item")
@Setter
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @OneToMany(
            mappedBy = "item",
            cascade = {CascadeType.ALL}
    )
    private List<PrescriptionItem> prescriptionItems;

}
