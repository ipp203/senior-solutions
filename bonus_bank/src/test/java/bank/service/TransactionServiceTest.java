package bank.service;

import bank.model.account.Account;
import bank.model.transaction.*;
import bank.repository.AccountRepository;
import bank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    ModelMapper modelMapper = new ModelMapper();

    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;

    TransactionService service;

    Transaction tr1;
    Transaction tr2;
    Transaction tr3;
    Account account1;
    Account account2;


    @BeforeEach
    void init() {
        service = new TransactionService(accountRepository, transactionRepository, modelMapper);

        tr1 = Transaction.ofCash(100, TransactionType.PAYMENT, 900, "12345678");
        tr2 = Transaction.ofTransfer(200, TransactionType.INPUT_TRANSFER, 900, "12345678", "87654321");
        tr3 = Transaction.ofTransfer(300, TransactionType.OUTPUT_TRANSFER, 900, "12345678", "88888888");

        account1 = new Account("AAA", "12345678", 1000, LocalDateTime.of(2021, 7, 27, 13, 0));
        account2 = new Account("BBB", "23456789", 2000, LocalDateTime.of(2021, 7, 27, 14, 0));
    }

    @Test
    void getTransactions() {
        when(transactionRepository.getTransactions())
                .thenReturn(List.of(tr1, tr2, tr3));

        List<TransactionDto> result = service.getTransactions();

        assertThat(result)
                .hasSize(3)
                .extracting("amount", "type")
                .containsExactly(
                        tuple(100, TransactionType.PAYMENT),
                        tuple(200, TransactionType.INPUT_TRANSFER),
                        tuple(300, TransactionType.OUTPUT_TRANSFER));

    }

    @Test
    void createCashTransaction() {
        account1.setBalance(1000);
        when(accountRepository.findActiveAccountByNumber(anyString()))
                .thenReturn(account1);

        TransactionDto result = service.createCashTransaction(new CreateCashTransactionCommand(200, account1.getAccountNumber()));

        assertEquals(1200, result.getBalanceAfterTransaction());
        assertEquals(TransactionType.DEPOSIT, result.getType());
    }

    @Test
    void createCashTransactionWithTooLargeAmount() {
        account1.setBalance(1000);
        when(accountRepository.findActiveAccountByNumber(anyString()))
                .thenReturn(account1);

        assertThrows(LittleBalanceException.class,
                () -> service.createCashTransaction(new CreateCashTransactionCommand(-2000, account1.getAccountNumber())));
    }

    @Test
    void createTransferTransaction() {
        account1.setBalance(1000);
        account2.setBalance(1000);
        when(accountRepository.findActiveAccountByNumber(account1.getAccountNumber()))
                .thenReturn(account1);
        when(accountRepository.findActiveAccountByNumber(account2.getAccountNumber()))
                .thenReturn(account2);

        TransactionDto result = service.createTransferTransaction(
                new CreateTransferTransactionCommand(200, account1.getAccountNumber(), account2.getAccountNumber()));

        assertAll(() -> {
                    assertEquals(800, result.getBalanceAfterTransaction());
                    assertEquals(TransactionType.OUTPUT_TRANSFER, result.getType());
                },
                () -> {
                    assertEquals(800, account1.getBalance());
                    assertEquals(1200, account2.getBalance());
                });
    }

    @Test
    void getTransfers() {
        when(transactionRepository.getTransfersByAccountNumber(account1.getAccountNumber()))
                .thenReturn(List.of(tr2,tr3));
        List<TransactionDto> result = service.getTransfers(Optional.of(account1.getAccountNumber()));

        verify(transactionRepository).getTransfersByAccountNumber(account1.getAccountNumber());
        assertEquals(2,result.size());
    }
    @Test
    void getTransfersWithEmptyNumber() {
        List<TransactionDto> result = service.getTransfers(Optional.empty());

        assertEquals(0,result.size());
    }
}