package io.swagger.Repository;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByDateBefore(Date date);

    List<Transaction> findByDateBetween(@NotNull @Valid Date startDate, @NotNull @Valid Date endDate);
}