package bank.model.account;

import bank.model.transaction.LittleBalanceException;
import bank.model.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account account;

    @BeforeEach
    void init(){
        account = new Account("aaa","12345678",1000, LocalDateTime.now());
    }


    @Test
    void testChangeBalance() {

        account.changeBalance(300, TransactionType.PAYMENT);
        assertEquals(700,account.getBalance());

        account.changeBalance(600, TransactionType.DEPOSIT);
        assertEquals(1300,account.getBalance());

        account.changeBalance(900, TransactionType.INPUT_TRANSFER);
        assertEquals(2200,account.getBalance());

        account.changeBalance(1200, TransactionType.OUTPUT_TRANSFER);
        assertEquals(1000,account.getBalance());

    }

    @Test
    void testChangeBalanceThrowsExecption(){
        Exception ex = assertThrows(LittleBalanceException.class,()-> account.changeBalance(1500,TransactionType.PAYMENT));
        assertEquals("Little Balance: Balance is too little, account number: 12345678",ex.getMessage());
    }
}