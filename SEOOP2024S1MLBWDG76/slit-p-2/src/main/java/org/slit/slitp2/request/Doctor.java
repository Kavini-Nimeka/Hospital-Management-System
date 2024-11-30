package org.slit.slitp2.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 02:39 PM
 */

@Setter
@Getter
public class Doctor {
    private Long id;
    private String name;
    private String special;
    private String degree;
    private Double price;
}
