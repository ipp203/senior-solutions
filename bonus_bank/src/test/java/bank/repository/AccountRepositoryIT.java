package bank.repository;

import bank.model.account.Account;
import bank.model.account.AccountNotFoundException;
import bank.model.account.AccountStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountRepositoryIT {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    AccountRepository repository;
    Account account1;
    Account account2;
    Account account3;

    @BeforeEach
    void init() {
        account1 = new Account("AAA", "12345678", 1000, LocalDateTime.of(2021, 7, 27, 13, 0));
        account2 = new Account("BBB", "23456789", 2000, LocalDateTime.of(2021, 7, 27, 14, 0));
        account3 = new Account("CcC", "34567890", 3000, LocalDateTime.of(2021, 7, 27, 15, 0));

        repository = new AccountRepository(factory);
    }

    @Test
    void listActiveAccounts() {
        repository.saveAccount(account1);
        account2.setStatus(AccountStatus.INACTIVE);
        repository.saveAccount(account2);
        repository.saveAccount(account3);

        List<Account> accounts = repository.listActiveAccounts();

        assertThat(accounts)
                .hasSize(2)
                .extracting("name", "balance")
                .containsExactly(tuple("AAA", 1000), tuple("CcC", 3000));
    }

    @Test
    void listAllAccounts() {
        repository.saveAccount(account1);
        repository.saveAccount(account2);
        repository.saveAccount(account3);

        List<Account> accounts = repository.listAllAccounts();

        assertThat(accounts)
                .hasSize(3)
                .extracting("name", "balance")
                .containsExactly(tuple("AAA", 1000), tuple("BBB", 2000), tuple("CcC", 3000));
    }

    @Test
    void updateAccountName() {
        repository.saveAccount(account1);
        Account accountId = repository.findActiveAccountByNumber(account1.getAccountNumber());
        repository.updateAccountName(accountId.getId(), "DDD");
        Account accountUpdated = repository.findActiveAccountByNumber(account1.getAccountNumber());

        assertEquals("DDD", accountUpdated.getName());
    }

    @Test
    void updateAccountBalance() {
        repository.saveAccount(account1);
        Account accountId = repository.findActiveAccountByNumber(account1.getAccountNumber());
        repository.updateAccountBalance(accountId.getId(), 5555);
        Account accountUpdated = repository.findActiveAccountByNumber(account1.getAccountNumber());

        assertEquals(5555, accountUpdated.getBalance());
    }

    @Test
    void deleteAccount() {
        repository.saveAccount(account1);
        repository.deleteAccount(account1.getId());
        List<Account> accountDeleted = repository.listAllAccounts();

        assertEquals(AccountStatus.INACTIVE,accountDeleted.get(0).getStatus());
    }

    @Test
    void findAccountByIdWithActiveAccount() {
        repository.saveAccount(account1);
        Account accountId = repository.findAccountById(account1.getId());
        assertEquals(1000, accountId.getBalance());
    }

    @Test
    void findAccountByIdWithInactiveAccount() {
        account1.setStatus(AccountStatus.INACTIVE);
        repository.saveAccount(account1);
        assertThrows(AccountNotFoundException.class,()-> repository.findAccountById(account1.getId()));
    }

    @Test
    void findActiveAccountByNumber() {
        repository.saveAccount(account1);
        Account account = repository.findActiveAccountByNumber(account1.getAccountNumber());
        assertEquals(account1.getAccountNumber(),account.getAccountNumber());
    }
}