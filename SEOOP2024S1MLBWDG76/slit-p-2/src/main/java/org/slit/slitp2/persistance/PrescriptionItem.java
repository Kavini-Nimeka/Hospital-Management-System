package org.slit.slitp2.persistance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 09:39 PM
 */


@Entity
@Table(name = "prescription_item")
@Setter
@Getter
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "prescription", referencedColumnName = "id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "item", referencedColumnName = "id")
    private Item item;

    @Column(name = "days")
    private Integer days;

    @Column(name = "note")
    private String note;

    @Column(name = "deleted")
    private Boolean deleted = false;
}
