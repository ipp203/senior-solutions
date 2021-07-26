package bank.transaction;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LittleBalanceException extends AbstractThrowableProblem {

    public LittleBalanceException(String accountNumber) {
        super(URI.create("transactions/little-balance"),
                "Little Balance",
                Status.BAD_REQUEST,
                "Balance is too little, account number: " + accountNumber);
    }

}
