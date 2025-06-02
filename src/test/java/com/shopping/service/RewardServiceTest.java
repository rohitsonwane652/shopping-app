package com.shopping.service;

import com.shopping.exception.InvalidDataException;
import com.shopping.exception.NotFoundException;
import com.shopping.exception.TransactionNotFoundException;
import com.shopping.model.RewardResponse;
import com.shopping.model.Transaction;
import com.shopping.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class RewardServiceTest {

    @InjectMocks
    RewardServiceImpl rewardServiceMock;

    @Mock
    TransactionRepository transactionRepoMock;

    /**
     * Test for calculateRewardForUser method for single user
     */
    @Test
    public void testCalculateRewards(){
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().orderId(1001L).itemId(1L).customerId(123L).amount(120).date(LocalDate.of(2025,05,25)).build(),
                Transaction.builder().orderId(1002L).itemId(1L).customerId(123L).amount(120).date(LocalDate.of(2025,04,25)).build()
        );

        when(transactionRepoMock.findLast3MonthsTransaction(anyLong()
                                , any(LocalDate.class))).thenReturn(transactions);

        RewardResponse result =
                RewardResponse.builder().customerId(123L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build();

        RewardResponse mockResult = assertDoesNotThrow(()-> rewardServiceMock.calculateRewardForUser(123L));

        assertEquals(result.getMonthlyPoints().entrySet(),mockResult.getMonthlyPoints().entrySet());

    }

    /**
     * Validating Exception Getting or not
     */
    @Test
    void testCalculateRewardForUser_EmptyTransactions(){
        when(transactionRepoMock.findLast3MonthsTransaction(anyLong()
                ,any(LocalDate.class))).thenReturn(Arrays.asList());

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class,
                            ()->rewardServiceMock.calculateRewardForUser(123L));

        String message = "No transactions found for customer ID: 123";

        Assertions.assertEquals(message, exception.getMessage());
    }

    /**
     * Validating correct customerId and throwing error test
     */
    @Test
    void testCalculateRewardForUser_InvalidCustomerId() {
        assertThrows(InvalidDataException.class, () -> rewardServiceMock.calculateRewardForUser(-1L));
    }

    /**
     * Successful test for calculateRewardForAllCustomers method
     */
    @Test
    void testCalculateRewardForAllCustomers_Success() {
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().orderId(1001L).itemId(1L).customerId(123L).amount(120).date(LocalDate.of(2025,05,25)).build(),
                Transaction.builder().orderId(1002L).itemId(1L).customerId(123L).amount(120).date(LocalDate.of(2025,04,25)).build(),
                Transaction.builder().orderId(1003L).itemId(1L).customerId(124L).amount(120).date(LocalDate.of(2025,05,25)).build(),
                Transaction.builder().orderId(1004L).itemId(1L).customerId(124L).amount(120).date(LocalDate.of(2025,04,25)).build()
        );

        when(transactionRepoMock.findAll()).thenReturn(transactions);

        List<RewardResponse> result = Arrays.asList(
                RewardResponse.builder().customerId(123L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build(),
                RewardResponse.builder().customerId(124L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build()
                );

        List<RewardResponse> mockResult = assertDoesNotThrow(()-> rewardServiceMock.calculateRewardForAllCustomers());

        assertEquals(result.size(),mockResult.size());

    }

    /**
     * Validating exception for no transactions in table
     */
    @Test
    public void testCalculateRewardForAllCustomers_NoTransactions() {
        when(transactionRepoMock.findAll()).thenReturn(Collections.emptyList());

        assertThrows(TransactionNotFoundException.class, () -> {
            rewardServiceMock.calculateRewardForAllCustomers();
        });

    }




}
