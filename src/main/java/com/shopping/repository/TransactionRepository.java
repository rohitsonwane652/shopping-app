package com.shopping.repository;

import com.shopping.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId AND t.date >= :startDate")
    List<Transaction> findLast3MonthsTransaction(Long customerId, LocalDate startDate);

}
