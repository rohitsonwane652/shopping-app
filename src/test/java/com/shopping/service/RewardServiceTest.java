package com.shopping.service;

import com.shopping.model.RewardResponse;
import com.shopping.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RewardServiceTest {

    @InjectMocks
    RewardService rewardServiceMock;

    @Test
    public void testCalculateRewards(){
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().customerId(123L).amount(120).date(LocalDate.of(2025,01,25)).build(),
                Transaction.builder().customerId(123L).amount(120).date(LocalDate.of(2025,02,25)).build(),
                Transaction.builder().customerId(124L).amount(120).date(LocalDate.of(2025,01,25)).build()
        );

        List<RewardResponse> result = Arrays.asList(
                RewardResponse.builder().customerId(123L)
                        .monthlyPoints(Map.of("FEBRUARY 2025",90,"JANUARY 2025",90)).totalPoints(180).build(),
                RewardResponse.builder().customerId(123L)
                        .monthlyPoints(Map.of("JANUARY 2025",90)).totalPoints(180).build()
        );

        List<RewardResponse> mockResult = Assertions.assertDoesNotThrow(()-> rewardServiceMock.calculateRewards(transactions));

        Assertions.assertEquals(result.get(0).getMonthlyPoints().entrySet(),mockResult.get(0).getMonthlyPoints().entrySet());


    }
}
