package org.slit.slitp2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 02:58 PM
 */

@Setter
@Getter
public class Patient {
    private Long id;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String address,username,password;
}
