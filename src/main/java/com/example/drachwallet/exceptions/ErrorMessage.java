package com.example.drachwallet.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessage {
    private LocalDateTime timeStamp;

    private String message ;

    private String description;

}
