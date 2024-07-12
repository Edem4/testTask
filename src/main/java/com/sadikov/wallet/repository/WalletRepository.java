package com.sadikov.wallet.repository;

import com.sadikov.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface WalletRepository extends JpaRepository<Wallet,String> {
    Optional<Wallet> findById(UUID idWallet);
}
