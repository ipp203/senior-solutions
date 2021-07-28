package bank.service;

import bank.model.account.Account;
import bank.model.account.AccountDto;
import bank.model.account.CreateAccountCommand;
import bank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository repository;

    ModelMapper modelMapper = new ModelMapper();
    AccountService service;

    Account account1;
    Account account2;
    Account account3;

    @BeforeEach
    void init() {
        service = new AccountService(repository, modelMapper);
        account1 = new Account("AAA", "12345678", 1000, LocalDateTime.of(2021, 7, 27, 13, 0));
        account2 = new Account("BBB", "23456789", 2000, LocalDateTime.of(2021, 7, 27, 14, 0));
        account3 = new Account("CcC", "34567890", 3000, LocalDateTime.of(2021, 7, 27, 15, 0));

    }


    @Test
    void createAccount() {
        AccountDto accountDto = service.createAccount(new CreateAccountCommand("AAA"));
        assertEquals(0, accountDto.getBalance());
        assertEquals("AAA", accountDto.getName());
    }

    @Test
    void listAccounts() {
        when(repository.listActiveAccounts()).thenReturn(List.of(account1, account2));
        List<AccountDto> result = service.listAccounts();

        assertThat(result)
                .hasSize(2);

        verify(repository).listActiveAccounts();

    }

    @Test
    void updateAccount() {
        when(repository.updateAccountName(1,"XXX"))
                .thenReturn(new Account("XXX","12345678",1000,LocalDateTime.now()));
        AccountDto result = service.updateAccount(1,new CreateAccountCommand("XXX"));

        assertEquals("XXX",result.getName());
        verify(repository).updateAccountName(1,"XXX");
    }

}