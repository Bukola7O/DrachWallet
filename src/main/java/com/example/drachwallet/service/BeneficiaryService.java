package com.example.drachwallet.service;

import com.example.drachwallet.dto.BeneficiaryDTO;
import com.example.drachwallet.exceptions.BeneficiaryException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.Beneficiary;

import java.util.List;

public interface BeneficiaryService {
    public Beneficiary addBeneficiary(Beneficiary beneficiary, String key) throws BeneficiaryException, CustomerException, WalletException;

    public List<Beneficiary> findAllByWallet(Integer walletId) throws BeneficiaryException;

    public Beneficiary viewBeneficiary(String beneficiaryName, String key) throws BeneficiaryException, CustomerException;

    public List<Beneficiary> viewAllBeneficiary(String key) throws BeneficiaryException, CustomerException;

    public Beneficiary deleteBeneficiary(String key, BeneficiaryDTO beneficiaryDTO) throws BeneficiaryException, CustomerException;
}
