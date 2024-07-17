package com.sadikov.wallet.controllers;

import com.sadikov.wallet.DTOs.WalletOperationDTO;
import com.sadikov.wallet.exception.BalanceException;
import com.sadikov.wallet.exception.WalletNotFoundException;
import com.sadikov.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<?> operation(@RequestBody WalletOperationDTO walletOperationDTO,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(Objects.requireNonNull(bindingResult.getFieldError()).getField(), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(walletService.replayOperation(walletOperationDTO), HttpStatus.OK);
        } catch (WalletNotFoundException | BalanceException  e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public ResponseEntity<?> getBalance(@PathVariable("WALLET_UUID") UUID walletId) {
        try {
            return new ResponseEntity<>(walletService.getBalanceToWallet(walletId), HttpStatus.OK);
        } catch (WalletNotFoundException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
