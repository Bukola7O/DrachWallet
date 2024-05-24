package com.example.drachwallet.repositories;

import com.example.drachwallet.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("FROM Customer c WHERE c.mobileNumber=?1")
    public List<Customer> findCustomerByMobile(String mobileNumber);
}
