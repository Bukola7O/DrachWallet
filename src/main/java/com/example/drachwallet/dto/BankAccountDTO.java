package com.example.drachwallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BankAccountDTO {
    @NotNull
    private Integer accountNo;

    @NotNull
    @Size(min = 11, max = 11, message = "Invalid BVN [Must be 11 digits]")
    private String BVN;

    @NotNull
    @Size(min = 3, max = 15,message = "Invalid Bank Name [ 3-15 characters only ]")
    private String bankName;

    @NotNull
    private Double balance;
}
