package bank.controller;


import bank.model.transaction.CreateCashTransactionCommand;
import bank.model.transaction.CreateTransferTransactionCommand;
import bank.model.transaction.TransactionDto;
import bank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transaction")
    public List<TransactionDto> getTransactions() {
        return service.getTransactions();
    }

    @GetMapping("/transaction/transfer")
    public List<TransactionDto> getTransfersByAccountNumber(@RequestParam Optional<String> accountNumber) {
        return service.getTransfers(accountNumber);
    }

    @PostMapping("/transaction/cash")
    public TransactionDto createCashTransaction(@RequestBody CreateCashTransactionCommand command){
        return service.createCashTransaction(command);
    }

    @PostMapping("/transaction/transfer")
    public TransactionDto createTransferTransaction(@RequestBody CreateTransferTransactionCommand command){
        return service.createTransferTransaction(command);
    }

}
