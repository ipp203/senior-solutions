package bank.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransferTransactionCommand {
    private int amount;

    private String accountNumber;

    private String targetAccountNumber;

}
