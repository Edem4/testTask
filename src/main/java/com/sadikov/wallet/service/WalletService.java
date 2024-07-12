package com.sadikov.wallet.service;

import com.sadikov.wallet.DTOs.WalletOperationDTO;
import com.sadikov.wallet.exception.WalletNotFoundException;
import com.sadikov.wallet.model.Wallet;
import com.sadikov.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Transactional
    public String replayOperation(WalletOperationDTO walletOperationDTO) throws WalletNotFoundException {

       Optional<Wallet> walletOptional = walletRepository.findById(UUID.fromString(walletOperationDTO.getWalletId()));
        if (walletOptional.isEmpty()) {
            throw new WalletNotFoundException("Wallet not found!");
        }
        Wallet wallet = walletOptional.get();
        switch (walletOperationDTO.getOperationType()) {
            case DEPOSIT -> wallet.setBalance(wallet.getBalance() + walletOperationDTO.getAmount());
            case WITHDRAW -> wallet.setBalance(wallet.getBalance() - walletOperationDTO.getAmount());
        }
        return "Operation was successfully completed!";
    }

    @Transactional(readOnly = true)
    public int getBalanceToWallet(UUID idWallet) throws WalletNotFoundException {
        Optional<Wallet> walletOptional = walletRepository.findById(idWallet);
        if (walletOptional.isEmpty()) {
            throw new WalletNotFoundException("Wallet not found!");
        }
        Wallet wallet = walletOptional.get();
        return wallet.getBalance();
    }
}
