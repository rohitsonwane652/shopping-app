package com.shopping.service;

import com.shopping.exception.NotFoundException;
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
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RewardServiceTest {

    @InjectMocks
    RewardServiceImpl rewardServiceMock;

    @Mock
    TransactionRepository transactionRepoMock;

    @Test
    public void testCalculateRewards(){
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().orderId(1001L).itemId(1L).customerId(123L).amount(120).date(LocalDate.of(2025,05,25)).build(),
                Transaction.builder().orderId(1002L).itemId(1L).customerId(123L).amount(120).date(LocalDate.of(2025,04,25)).build()
        );

        Mockito.when(transactionRepoMock.findLast3MonthsTransaction(ArgumentMatchers.anyLong()
                                , ArgumentMatchers.any(LocalDate.class))).thenReturn(transactions);

        RewardResponse result =
                RewardResponse.builder().customerId(123L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build();

        RewardResponse mockResult = Assertions.assertDoesNotThrow(()-> rewardServiceMock.calculateRewardForUser(123L));

        Assertions.assertEquals(result.getMonthlyPoints().entrySet(),mockResult.getMonthlyPoints().entrySet());

    }

    @Test
    void testCalculateRewardForUserException(){
        Mockito.when(transactionRepoMock.findLast3MonthsTransaction(ArgumentMatchers.anyLong()
                ,ArgumentMatchers.any(LocalDate.class))).thenReturn(Arrays.asList());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                            ()->rewardServiceMock.calculateRewardForUser(123L));

        String message = "No transactions found for customer ID: 123";

        Assertions.assertEquals(message, exception.getMessage());
    }
}
