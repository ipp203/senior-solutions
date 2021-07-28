package bank.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransferTransactionCommand {
    private int amount;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String targetAccountNumber;

}
