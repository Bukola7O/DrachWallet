package com.example.drachwallet.repositories;

import com.example.drachwallet.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
