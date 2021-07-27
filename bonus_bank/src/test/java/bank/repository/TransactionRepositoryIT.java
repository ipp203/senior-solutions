package bank.repository;

import bank.model.account.Account;
import bank.model.transaction.Transaction;
import bank.model.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryIT {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    TransactionRepository repository;

    Transaction tr1;
    Transaction tr2;
    Transaction tr3;

    @BeforeEach
    void init(){
        repository = new TransactionRepository(factory);
        tr1 = Transaction.ofCash(100,TransactionType.PAYMENT,900,"12345678");
        tr2 = Transaction.ofTransfer(200,TransactionType.INPUT_TRANSFER,900,"12345678","87654321");
        tr3 = Transaction.ofTransfer(300,TransactionType.OUTPUT_TRANSFER,900,"12345678","88888888");
    }

    @Test
    void testSaveAndGet() {
        repository.save(tr1);
        repository.save(tr2);

        List<Transaction> transactions = repository.getTransactions();

        assertThat(transactions)
                .hasSize(2)
                .extracting("amount", "type")
                .containsExactly(tuple(100,TransactionType.PAYMENT),tuple(200,TransactionType.INPUT_TRANSFER));
    }

    @Test
    void testGetTransfersByAccountNumber() {
        repository.save(tr1);
        repository.save(tr2);
        repository.save(tr3);

        List<Transaction> transactions = repository.getTransfersByAccountNumber("12345678");

        assertThat(transactions)
                .hasSize(2)
                .extracting("amount","type")
                .containsExactly(tuple(200,TransactionType.INPUT_TRANSFER),tuple(300,TransactionType.OUTPUT_TRANSFER));
    }

    @Test
    void testGetTransfersByWrongAccountNumber() {
        repository.save(tr1);
        repository.save(tr2);
        repository.save(tr3);

        List<Transaction> transactions = repository.getTransfersByAccountNumber("88888888");

        assertThat(transactions)
                .hasSize(0);
    }
}