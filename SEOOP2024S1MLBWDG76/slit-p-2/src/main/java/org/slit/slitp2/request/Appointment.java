package org.slit.slitp2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 12:32 AM
 */

@Setter
@Getter
public class Appointment {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservedTime;
    private Integer patient,doctor;
    private String patientName,doctorName;
    private Double amount;
}
