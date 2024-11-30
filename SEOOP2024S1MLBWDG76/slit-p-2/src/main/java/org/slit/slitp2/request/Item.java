package org.slit.slitp2.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 10:37 PM
 */

@Setter
@Getter
public class Item {
    private Integer id;
    private String name;
    private Double price;
}
