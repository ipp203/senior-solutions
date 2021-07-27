package bank.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private long id;

    private int amount;

    private TransactionType type;

    private LocalDateTime time;

    private int balanceAfterTransaction;

    private String accountNumber;

    private String targetAccountNumber;
}
