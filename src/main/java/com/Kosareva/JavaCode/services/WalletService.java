package com.Kosareva.JavaCode.services;

import com.Kosareva.JavaCode.DTO.WalletDTO;
import com.Kosareva.JavaCode.Repositories.OperationRepository;
import com.Kosareva.JavaCode.Repositories.WalletRepository;
import com.Kosareva.JavaCode.Util.OperationNotCreatedException;
import com.Kosareva.JavaCode.Util.WalletNotFoundException;
import com.Kosareva.JavaCode.models.Operation;
import com.Kosareva.JavaCode.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class WalletService {
    private final WalletRepository walletRepository;
    private final OperationRepository operationRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository, OperationRepository operationRepository) {
        this.walletRepository = walletRepository;
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Wallet save(Wallet wallet) {
        walletRepository.save(wallet);
        return wallet;
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet findById(UUID id) {
        Optional<Wallet> foundWallet = walletRepository.findById(id);
        return foundWallet.orElseThrow(WalletNotFoundException::new);
    }

    public WalletDTO findDTOById(UUID id) {
        Optional<Wallet> foundWallet = walletRepository.findById(id);
        return converToWalletDTO(foundWallet.orElseThrow(WalletNotFoundException::new));
    }


    @Transactional
    public Operation save(Operation operation) {
        Wallet wallet = walletRepository.findById(operation.getWallet().getId()).orElseThrow(WalletNotFoundException::new);
        if (operation.getOperationType().equals(Operation.OperationType.WITHDRAW)
                && wallet.getBalance() < operation.getAmount()) {
            throw new OperationNotCreatedException("Have not enough money on balance");
        }
        if (operation.getOperationType().equals(Operation.OperationType.WITHDRAW)) {
            wallet.setBalance(wallet.getBalance() - operation.getAmount());
            walletRepository.save(wallet);
        }
        if (operation.getOperationType().equals(Operation.OperationType.DEPOSIT)) {
            wallet.setBalance(wallet.getBalance() + operation.getAmount());
            walletRepository.save(wallet);
        }

        operationRepository.save(operation);

        return operation;
    }

    private WalletDTO converToWalletDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setName(wallet.getName());
        walletDTO.setBalance(wallet.getBalance());
        return walletDTO;
    }

}
