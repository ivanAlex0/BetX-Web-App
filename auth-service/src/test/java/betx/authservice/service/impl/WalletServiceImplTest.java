package betx.authservice.service.impl;

import betx.authservice.model.Wallet;
import betx.authservice.repository.WalletRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceImplTest {

    @Mock
    WalletRepository walletRepository;

    @InjectMocks
    WalletServiceImpl walletService;

    @Test
    public void testDeposit_happyFlow() {
        Mockito.when(walletRepository.findById(Mockito.any())).thenReturn(Optional.of(validWallet()));

        Wallet _wallet = walletService.deposit(validWallet(), 20f);
        Assertions.assertEquals(_wallet.getBalance(), validWallet().getBalance() + 20f);
    }

    @Test(expected = RuntimeException.class)
    public void testWithdraw_notEnoughAmount() {
        Mockito.when(walletRepository.findById(Mockito.any())).thenReturn(Optional.of(validWallet()));

        Wallet _wallet = walletService.withdraw(validWallet(), 30f);
    }

    @Test
    public void testWithdraw_happyFlow() {
        Mockito.when(walletRepository.findById(Mockito.any())).thenReturn(Optional.of(validWallet()));

        Wallet _wallet = walletService.withdraw(validWallet(), 10f);
        Assertions.assertEquals(_wallet.getBalance(), validWallet().getBalance() - 10f);
    }

    public Wallet validWallet() {
        return Wallet.builder()
                .walletId(12L)
                .balance(20.0)
                .build();
    }
}
