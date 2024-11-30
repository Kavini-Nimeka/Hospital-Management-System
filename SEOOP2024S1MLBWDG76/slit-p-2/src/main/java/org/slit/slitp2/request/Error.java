package org.slit.slitp2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @Author     
 * @Project ise
 * @Date 2024-03-20 12:03 PM
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Error {
    private Integer status;
    private String error;
    private String message;
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public Error(HttpStatus httpStatus, String message, String path, LocalDateTime timestamp) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }
}
