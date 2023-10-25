package io.swagger.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import io.swagger.Repository.TransactionRepository;
import io.swagger.model.Transaction;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction getTransactionById(Integer transactionId) {
        try {
            // Retrieve the transaction by ID
            Transaction transaction = transactionRepository.findById(transactionId).orElse(null);

            if (transaction != null) {
                // If the transaction exists, create a list and add it for printing
                List<Transaction> transactions = new ArrayList<>();
                transactions.add(transaction);
                printTransactionTable(transactions);
            } else {
                // Handle the case where the transaction is not found
                System.out.println("Transaction not found with ID: " + transactionId);
            }

            return transaction;
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
            return null; // Return null to indicate an error
        }
    }

    public void getTransactionsBetweenDates(@NotNull @Valid Date startDate, @NotNull @Valid Date endDate) {
        try {
            // Retrieve transactions between the specified dates
            List<Transaction> transactions = transactionRepository.findByDateBetween(startDate, endDate);

            if (!transactions.isEmpty()) {
                // If transactions exist, print them in a table
                printTransactionTable(transactions);
            } else {
                // Handle the case where no transactions are found within the date range
                System.out.println("No transactions found between " + startDate + " and " + endDate);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
        }
    }

    public void getAllTransactions() {
        try {
            // Retrieve all transactions
            List<Transaction> transactions = transactionRepository.findAll();

            if (!transactions.isEmpty()) {
                // If transactions exist, print them in a table
                printTransactionTable(transactions);
            } else {
                // Handle the case where there are no transactions in the repository
                System.out.println("No transactions found in the repository.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
        }
    }

    public void printTransactionTable(List<Transaction> transactions) {
        // Determine the maximum widths for each column
        int idWidth = 20;
        int dateWidth = 30;
        int descriptionWidth = 20;
        int typeWidth = 20;
        int amountWidth = 20;

        for (Transaction transaction : transactions) {
            descriptionWidth = Math.max(descriptionWidth, transaction.getDescription().length());
            typeWidth = Math.max(typeWidth, transaction.getType().length());
            // Assuming 'Amount' is a double, convert it to a formatted string and calculate
            // its width
            String amountString = String.format("$%,-10.2f", transaction.getAmount());
            amountWidth = Math.max(amountWidth, amountString.length());
        }

        // Use the determined column widths for formatting
        String format = String.format("%%-%ds %%-%ds %%-%ds %%-%ds %%-%ds%%n",
                idWidth, dateWidth, descriptionWidth, typeWidth, amountWidth);

        System.out.printf(format, "Transaction ID", "Date", "Description", "Type", "Amount");

        for (Transaction transaction : transactions) {
            System.out.printf(format,
                    transaction.getId(),
                    transaction.getDate(),
                    transaction.getDescription(),
                    transaction.getType(),
                    String.format("$%,-10.2f", transaction.getAmount()));
        }
    }

}
