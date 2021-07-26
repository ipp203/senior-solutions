package bank.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountWithTransactionsDto {
    private long id;
    private String name;
    private String accountNumber;
    private int balance;
}
