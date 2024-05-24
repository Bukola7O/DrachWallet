package com.example.drachwallet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Beneficiary {
    @Id
    @NotNull
    @Size(min = 10, max = 10 ,message = "Invalid Mobile Number [ 10 Digit Only ] ")
    private String beneficiaryMobileNumber;

    @NotNull
    @Size(min = 3, message = "Invalid Beneficiary name [ contains at least 3 characters ]")
    private String beneficiaryName;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "walletId" ,referencedColumnName = "walletId")
    private Wallet wallet;

    public Beneficiary(String beneficiaryMobileNumber, String beneficiaryName) {
        this.beneficiaryMobileNumber = beneficiaryMobileNumber;
        this.beneficiaryName = beneficiaryName;
    }
}
