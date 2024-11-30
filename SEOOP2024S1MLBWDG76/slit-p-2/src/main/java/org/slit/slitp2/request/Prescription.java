package org.slit.slitp2.request;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 11:35 PM
 */

@Setter
@Getter
public class Prescription {
    private Integer id;
    private LocalDate date;
    private Appointment appointment;
    private List<PrescriptionItem> prescriptionItems;
}
