package betx.authservice.service.services;

import betx.authservice.model.users.Customer;
import betx.authservice.model.Wallet;

public interface WalletService {
    Wallet update(Wallet wallet);

    Wallet save(Wallet wallet);

    Wallet save(Customer customer);

    Wallet deposit(Wallet wallet, Float amount);

    Wallet withdraw(Wallet wallet, Float amount);

}
