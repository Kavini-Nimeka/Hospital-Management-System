package org.slit.slitp2.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-11 11:26 AM
 */

@Entity
@Table(name = "admin")
@Setter
@Getter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = "username"
    )
    private String username;
    @Column(
            name = "password"
    )
    private String password;
}
