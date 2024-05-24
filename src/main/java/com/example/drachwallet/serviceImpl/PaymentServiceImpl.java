package com.example.drachwallet.serviceImpl;

import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.PaymentException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.Payment;
import com.example.drachwallet.repositories.PaymentRepository;
import com.example.drachwallet.service.PaymentService;
import com.example.drachwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private WalletService walletService;

    @Autowired
    private PaymentRepository PaymentRepository;

    @Override
    public String addPayment(String targetMobile, String Name, double amount, String BillType, LocalDate paymentDate, Integer walletId, String key) throws PaymentException, WalletException, CustomerException, TransactionException {
        BigDecimal value = BigDecimal.valueOf(amount);

//		String str = walletService.fundTransfer(targetMobile, Name, value, key);

        Payment billPayment = new Payment(amount, BillType, LocalDate.now());

        PaymentRepository.save(billPayment);

//		return str;

        return "";
    }
}
