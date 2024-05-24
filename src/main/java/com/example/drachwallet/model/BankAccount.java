package com.example.drachwallet.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {
    @Id
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


    @ManyToOne(cascade= CascadeType.PERSIST)
    private Wallet wallet;


    public BankAccount(@NotNull Integer accountNo, @NotNull String BVN,
                       @NotNull @Size(min = 4, max = 10, message = "Bank name not valid") String bankName,
                       @NotNull Double balance) {
        super();
        this.accountNo = accountNo;
        this.BVN = BVN;
        this.bankName = bankName;
        this.balance = balance;
    }
}
