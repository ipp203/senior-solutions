package bank.model.account;

import bank.model.transaction.TransactionType;
import bank.model.transaction.LittleBalanceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @Column(unique = true, length = 8)
    private String accountNumber;

    private int balance = 0;

    private LocalDateTime openingDate;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    public Account(String name, String accountNumber, int balance, LocalDateTime openingDate) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.openingDate = openingDate;
        status = AccountStatus.ACTIVE;
    }


    public int changeBalance(int amount, TransactionType type) {
        hasEnoughBalance(amount,type);

        if (type == TransactionType.INPUT_TRANSFER || type == TransactionType.DEPOSIT) {
            balance += amount;
        } else {
            balance -= amount;
        }
        return balance;
    }

    private void hasEnoughBalance(int amount, TransactionType type) {
        if (type == TransactionType.OUTPUT_TRANSFER || type == TransactionType.PAYMENT) {
            if (balance < amount) {
                throw new LittleBalanceException(accountNumber);
            }
        }
    }


}
