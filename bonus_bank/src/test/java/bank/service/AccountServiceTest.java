package bank.service;

import bank.model.account.CreateAccountCommand;
import bank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Autowired
    ModelMapper modelMapper;

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountService service;



    @Test
    void createAccount() {
//        service.
//        service.createAccount(new CreateAccountCommand("AAA"));
    }

    @Test
    void listAccounts() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void getAccountById() {
    }
}