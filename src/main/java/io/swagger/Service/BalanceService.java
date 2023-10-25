package io.swagger.Service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.Repository.TransactionRepository;
import io.swagger.model.Transaction;

@Service
public class BalanceService {
    @Autowired
    private final TransactionRepository transactionRepository;

    public BalanceService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public double getBalanceTillDate(Date date) {
        try {
            // Initialize the total balance to 0.0
            double totalBalance = 0.0;

            // Retrieve all transactions from the repository until the provided date
            Iterable<Transaction> transactions = transactionRepository.findByDateBefore(date);

            // Process each transaction and update the total balance
            for (Transaction transaction : transactions) {
                if ("Credit".equals(transaction.getType())) {
                    totalBalance += transaction.getAmount();
                } else if ("Debit".equals(transaction.getType())) {
                    totalBalance -= transaction.getAmount();
                }
            }

            return totalBalance;
        } catch (Exception e) {
            // Handle exceptions and print appropriate error messages
            e.printStackTrace();
            return -1.0; // or any other error value
        }
    }
}
