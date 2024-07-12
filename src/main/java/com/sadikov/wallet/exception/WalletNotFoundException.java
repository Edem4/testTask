package com.sadikov.wallet.exception;

import com.sadikov.wallet.repository.WalletRepository;

public class WalletNotFoundException extends Exception{
    public WalletNotFoundException(String massage){
        super(massage);
    }
}
