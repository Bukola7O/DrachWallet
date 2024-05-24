package com.example.drachwallet.serviceImpl;

import com.example.drachwallet.dto.BeneficiaryDTO;
import com.example.drachwallet.exceptions.BeneficiaryException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.Beneficiary;
import com.example.drachwallet.model.CurrentUserSession;
import com.example.drachwallet.model.Customer;
import com.example.drachwallet.model.Wallet;
import com.example.drachwallet.repositories.BeneficiaryRepository;
import com.example.drachwallet.repositories.CurrentUserSessionRepository;
import com.example.drachwallet.repositories.CustomerRepository;
import com.example.drachwallet.repositories.WalletRepository;
import com.example.drachwallet.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Beneficiary addBeneficiary(Beneficiary beneficiary, String key) throws BeneficiaryException, CustomerException, WalletException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<Customer> customer = customerRepository.findById(currentUserSession.getUserId());
        Optional<Wallet> wallet = walletRepository.findById(walletRepository.showCustomerWalletDetails(currentUserSession.getUserId()).getWalletId());

        if (!customer.isPresent()) {
            throw new CustomerException("Beneficiary is not Registered to the Application.");
        }

        if (!wallet.isPresent()){
            throw new WalletException("Invalid User.");
        }

        Optional<Beneficiary> optional=beneficiaryRepository.findById(beneficiary.getBeneficiaryMobileNumber());

        if(optional.isEmpty()) {
            return beneficiaryRepository.save(beneficiary);
        }
        throw new BeneficiaryException("Duplicate Details [ Beneficiary Already Exist ]");

    }

    @Override
    public List<Beneficiary> findAllByWallet(Integer walletId) throws BeneficiaryException {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findByWallet(walletId);

        if(beneficiaries.isEmpty()) {
            throw new BeneficiaryException("No Beneficiary Exist");
        }
        return beneficiaries;

    }

    @Override
    public Beneficiary viewBeneficiary(String beneficiaryName, String key) throws BeneficiaryException, CustomerException {
        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepository.showCustomerWalletDetails(currUserSession.getUserId());

        Beneficiary beneficiaries = beneficiaryRepository.findByNameWallet(wallet.getWalletId(),beneficiaryName);

        if(beneficiaries == null) {
            throw new BeneficiaryException("No Beneficiary Exist");
        }
        return beneficiaries;
    }

    @Override
    public List<Beneficiary> viewAllBeneficiary(String key) throws BeneficiaryException, CustomerException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepository.showCustomerWalletDetails(currentUserSession.getUserId());

        List<Beneficiary> beneficiaries = beneficiaryRepository.findByWallet(wallet.getWalletId());

        if(beneficiaries.isEmpty()) {
            throw new BeneficiaryException("No Beneficiary Exist");
        }
        return beneficiaries;
    }

    @Override
    public Beneficiary deleteBeneficiary(String key, BeneficiaryDTO beneficiaryDTO) throws BeneficiaryException, CustomerException {

        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepository.showCustomerWalletDetails(currentUserSession.getUserId());

        Beneficiary beneficiaries = beneficiaryRepository.findByMobileWallet(wallet.getWalletId(),beneficiaryDTO.getBeneficiaryMobileNumber());

        if(beneficiaries == null) {
            throw new BeneficiaryException("No Beneficiary found");
        }

        beneficiaryRepository.delete(beneficiaries);

        return beneficiaries;
    }
}
