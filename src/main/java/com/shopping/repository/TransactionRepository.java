package com.shopping.repository;

import com.shopping.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Custom JPQL query to fetch all transactions for a given customer
     * that occurred on or after the specified start date.
     * @param customerId - id of customer for which transactions will be fetched
     * @param startDate - startDate the date from which to start fetching transactions
     * @return list of Transaction
     */
    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId AND t.date >= :startDate")
    List<Transaction> findLast3MonthsTransaction(Long customerId, LocalDate startDate);

    /**
     * Custom JPQL query to fetch all transactions after the specified start date.
     * @param startDate - startDate the date from which to start fetching transactions
     * @return list of Transaction
     */
    @Query("SELECT t FROM Transaction t WHERE t.date >= :startDate")
    List<Transaction> findLast3MonthsTransactionForAllUsers( LocalDate startDate);
}
