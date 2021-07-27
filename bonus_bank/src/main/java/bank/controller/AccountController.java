package bank.controller;

import bank.model.account.AccountDto;
import bank.model.account.AccountWithTransactionsDto;
import bank.model.account.CreateAccountCommand;
import bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/account")
    public AccountDto createAccount(@RequestBody CreateAccountCommand command) {
        return service.createAccount(command);
    }

    @GetMapping("/account")
    public List<AccountDto> listAccounts() {
        return service.listAccounts();
    }

    @GetMapping("/account/{id}")
    public AccountWithTransactionsDto getAccount(@PathVariable("id") long id) {
        return service.getAccountById(id);
    }

    @PutMapping("/account/{id}")
    public AccountDto updateAccount(@PathVariable("id") long id, @RequestBody CreateAccountCommand command) {
        return service.updateAccount(id, command);
    }

    @DeleteMapping("/account/{id}")
    public void deleteAccount(@PathVariable("id") long id){
        service.deleteAccount(id);
    }


}
