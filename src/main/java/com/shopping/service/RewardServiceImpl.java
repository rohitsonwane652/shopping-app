package com.shopping.service;

import com.shopping.exception.NotFoundException;
import com.shopping.model.RewardResponse;
import com.shopping.model.Transaction;
import com.shopping.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Calculates Reward Point based on Last 3 month transaction amount
     * @param customerId - fetcging transaction based on customerId
     * @return
     */
    @Override
    public RewardResponse calculateRewardForUser(Long customerId) {
        if(customerId==null || customerId<=0){
            throw new IllegalArgumentException("Customer Id must be positive");
        }
        LocalDate localDate = LocalDate.now();
        LocalDate startDate = localDate.minusMonths(3);

        //Fetch Last 3 months transactions
        List<Transaction> transactions = transactionRepository.findLast3MonthsTransaction(customerId,startDate);

        if(transactions.isEmpty()){
            throw new NotFoundException("No transactions found for customer ID: " + customerId);
        }

        Map<String, Integer> customerMonthlyPoints = new HashMap<>();

        //Calculating Monthly points
        for (Transaction transaction : transactions) {
            String month = transaction.getDate().getMonth().toString() + " " + transaction.getDate().getYear();
            int points = calculatePoints(transaction.getAmount());

            int prevPoints = customerMonthlyPoints.getOrDefault(month, 0);
            customerMonthlyPoints.put(month, prevPoints + points);
        }

        int total = customerMonthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

        RewardResponse response = RewardResponse.builder().customerId(customerId)
                                    .monthlyPoints(customerMonthlyPoints).totalPoints(total).build();


        return response;
    }

    /**
     *
     * @param amount to calculate reward points
     * 2 points for every dollar spent over $100
     * 1 point for every dollar spent between $50 and $100
     */
    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) (2 * (amount - 100));
            points += 50;
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }
}
