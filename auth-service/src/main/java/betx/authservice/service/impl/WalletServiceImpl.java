package betx.authservice.service.impl;

import betx.authservice.model.users.Customer;
import betx.authservice.model.Wallet;
import betx.authservice.repository.WalletRepository;
import betx.authservice.service.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet update(Wallet wallet){
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet save(Wallet wallet) {
        Wallet _wallet = walletRepository.save(
                Wallet.builder()
                        .balance(0.0)
                        .customer(wallet.getCustomer())
                        .build()
        );
        log.info("New Wallet added for {User.userId}=" + wallet.getCustomer().getUser().getUserId());
        return _wallet;
    }

    @Override
    public Wallet save(Customer _customer) {
        Wallet _wallet = walletRepository.save(
                Wallet.builder()
                        .balance(0.0)
                        .customer(_customer)
                        .build()
        );
        log.info("New Wallet added with {wallet.walletId}=" + _wallet.getWalletId());
        return _wallet;
    }

    @Override
    public Wallet deposit(Wallet wallet, Float amount) {
        Wallet _wallet = walletRepository.findById(wallet.getWalletId()).orElseThrow(
                () -> new RuntimeException("No Wallet found for {walletId}= " + wallet.getWalletId())
        );

        _wallet.setBalance(_wallet.getBalance() + amount);
        log.info("Amount of " + amount + " was added to wallet {walletId}=" + _wallet.getWalletId());
        return _wallet;
    }

    @Override
    public Wallet withdraw(Wallet wallet, Float amount) {
        Wallet _wallet = walletRepository.findById(wallet.getWalletId()).orElseThrow(
                () -> new RuntimeException("No Wallet found for {walletId}= " + wallet.getWalletId())
        );

        if (_wallet.getBalance() < amount) {
            log.error("Trial to withdraw more than Wallet has for {User.userId}=" + wallet.getCustomer().getUser().getUserId());
            throw new RuntimeException("Your Wallet doesn't have the amount requested to withdraw");
        } else {
            _wallet.setBalance(_wallet.getBalance() - amount);
            log.info("Wallet with {walletId}=" + _wallet.getWalletId() + " has just withdrawn the amount of " + amount);
            return _wallet;
        }
    }
}
