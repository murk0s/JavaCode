package com.Kosareva.JavaCode.Repositories;

import com.Kosareva.JavaCode.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface WalletRepository  extends JpaRepository<Wallet, UUID> {
}
