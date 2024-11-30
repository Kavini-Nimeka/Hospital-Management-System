package org.slit.slitp2.request;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 11:38 PM
 */

@Setter
@Getter
public class PrescriptionItem {
    private Integer id;
    private Item item;
    private Integer days;
    private String note;
}
