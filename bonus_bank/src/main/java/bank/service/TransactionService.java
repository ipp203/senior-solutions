package bank.service;

import bank.repository.AccountRepository;
import bank.model.account.Account;
import bank.model.transaction.*;
import bank.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    public List<TransactionDto> getTransactions() {
        List<Transaction> transactions = transactionRepository.getTransactions();
        Type targetListType = new TypeToken<List<TransactionDto>>() {}.getType();
        return modelMapper.map(transactions, targetListType);
    }

    public TransactionDto createCashTransaction(CreateCashTransactionCommand command) {
        Account account = accountRepository.findActiveAccountByNumber(command.getAccountNumber());

        TransactionType type = command.getAmount() > 0 ? TransactionType.DEPOSIT : TransactionType.PAYMENT;

        int newBalance = account.changeBalance(Math.abs(command.getAmount()), type);
        accountRepository.updateAccountBalance(account.getId(), account.getBalance());

        Transaction transaction = Transaction.ofCash(command.getAmount(), type, newBalance, command.getAccountNumber());
        transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }

    public TransactionDto createTransferTransaction(CreateTransferTransactionCommand command) {
        Account account = accountRepository.findActiveAccountByNumber(command.getAccountNumber());
        Account targetAccount = accountRepository.findActiveAccountByNumber(command.getTargetAccountNumber());

        int newBalance = account.changeBalance(command.getAmount(), TransactionType.OUTPUT_TRANSFER);
        targetAccount.changeBalance(command.getAmount(), TransactionType.INPUT_TRANSFER);
        accountRepository.updateAccountBalance(account.getId(), account.getBalance());
        accountRepository.updateAccountBalance(targetAccount.getId(), targetAccount.getBalance());

        Transaction transactionOut = Transaction.ofTransfer(command.getAmount(), TransactionType.OUTPUT_TRANSFER, newBalance, command.getAccountNumber(), command.getTargetAccountNumber());
        Transaction transactionIn = Transaction.ofTransfer(command.getAmount(), TransactionType.INPUT_TRANSFER, newBalance, command.getTargetAccountNumber(), command.getAccountNumber());
        transactionRepository.save(transactionOut);
        transactionRepository.save(transactionIn);
        return modelMapper.map(transactionOut, TransactionDto.class);
    }

    public List<TransactionDto> getTransfers(Optional<String> accountNumber) {
        if (accountNumber.isEmpty()){
            return Collections.emptyList();
        }
        accountRepository.findActiveAccountByNumber(accountNumber.get());
        List<Transaction> result = transactionRepository.getTransfersByAccountNumber(accountNumber.get());

        Type targetListType = new TypeToken<List<TransactionDto>>(){}.getType();
        return modelMapper.map(result, targetListType);

    }
}
