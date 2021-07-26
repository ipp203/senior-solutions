package bank.account;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final ModelMapper modelMapper;

    public AccountService(AccountRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public AccountDto createAccount(CreateAccountCommand command) {
        Account account = new Account(command.getName(), generateAccountNumber(), 0, LocalDateTime.now());
        repository.saveAccount(account);
        return modelMapper.map(account,AccountDto.class);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return IntStream.rangeClosed(1, 8)
                .map(i -> random.nextInt(10))
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

    }

    public List<AccountDto> listAccounts() {
        List<Account> result = repository.listActiveAccounts();
        Type targetListType = new TypeToken<List<AccountDto>>(){}.getType();

        return modelMapper.map(result,targetListType);

    }

    public AccountDto updateAccount(long id, CreateAccountCommand command) {
        Account result = repository.updateAccountName(id,command.getName());
        return modelMapper.map(result,AccountDto.class);
    }

    public void deleteAccount(long id) {
        repository.deleteAccount(id);
    }

    public AccountWithTransactionsDto getAccountById(long id) {
        Account account = repository.findAccountById(id);
        return modelMapper.map(account,AccountWithTransactionsDto.class);
    }


}
