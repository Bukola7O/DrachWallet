package com.example.drachwallet.repositories;

import com.example.drachwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    @Query("FROM Wallet w INNER JOIN w.customer c WHERE c.customerId=?1")
    public Wallet showCustomerWalletDetails(Integer customerId);
}
