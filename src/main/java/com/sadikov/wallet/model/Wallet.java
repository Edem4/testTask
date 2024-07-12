package com.sadikov.wallet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @Column(name = "id_wallet")
    UUID id;
    @Column
    int balance;
}
