package com.Kosareva.JavaCode.controllers;

import com.Kosareva.JavaCode.DTO.WalletDTO;
import com.Kosareva.JavaCode.models.Wallet;
import com.Kosareva.JavaCode.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("wallet", walletService.findAll());
        return "wallet/index";
    }
    @GetMapping("/new")
    public String newWallet(@ModelAttribute("wallet") Wallet wallet) {
        return "wallet/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("wallet") WalletDTO walletDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "wallet/new";

        walletService.save(converToWallet(walletDTO));
        return "redirect:/wallet";
    }

    private Wallet converToWallet(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setName(walletDTO.getName());
        wallet.setBalance(0.0d);
        return wallet;
    }


}
