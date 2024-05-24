package com.example.drachwallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;

    @NotNull
    @Size(min = 3, message = "Invalid Customer name [ should contain at least 3 characters ]")
    private String customerName;

    @NotNull
    @Size(min = 11, max = 11 ,message = "Invalid Mobile Number [ 11 Digit Only ] ")
    private String mobileNumber;

    @NotNull
    @Size(min = 6, max = 12, message = "Invalid Password [ must be 6 to 8 characters ]")
    private String password;

}
