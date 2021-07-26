package bank.account;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class AccountNotFoundException extends AbstractThrowableProblem {
    public AccountNotFoundException(Long id){
        super(URI.create("accounts/account-not-found"),
                "Not found",
                Status.NOT_FOUND,
                "Account not found with id: " + id);
    }

    public AccountNotFoundException(String accountNumber){
        super(URI.create("accounts/account-not-found"),
                "Not found",
                Status.NOT_FOUND,
                "Account not found with account number: " + accountNumber);
    }
}
