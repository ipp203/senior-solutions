package bank.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int amount;

    @Enumerated(value = EnumType.STRING)
    private TransactionType type;

    private LocalDateTime time;

    private int balanceAfterTransaction;

    private String accountNumber;

    private String targetAccountNumber;

    private Transaction(int amount, TransactionType type, LocalDateTime time, int balanceAfterTransaction, String accountNumber, String targetAccountNumber) {
        this.amount = amount;
        this.type = type;
        this.time = time;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.accountNumber = accountNumber;
        this.targetAccountNumber = targetAccountNumber;
    }

    public static Transaction ofCash(int amount, TransactionType type, int balanceAfterTransaction, String accountNumber) {
        return new Transaction(amount, type, LocalDateTime.now(), balanceAfterTransaction, accountNumber, accountNumber);
    }

    public static Transaction ofTransfer(int amount, TransactionType type, int balanceAfterTransaction, String accountNumber, String targetAccountNumber) {
        return new Transaction(amount, type, LocalDateTime.now(), balanceAfterTransaction, accountNumber, targetAccountNumber);
    }

}
