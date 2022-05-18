package betx.authservice.service.services;

import betx.authservice.model.Wallet;

public interface WalletService {
    Wallet save(Wallet wallet);

    Wallet deposit(Wallet wallet, Double amount);

    Wallet withdraw(Wallet wallet, Double amount);

}
