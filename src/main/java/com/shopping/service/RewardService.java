package com.shopping.service;

import com.shopping.model.RewardResponse;
import com.shopping.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    /**
     * Calculates Reward Point based on transcation amount
     *
     * @param transactions - list of transctions of months of users
     */
    public List<RewardResponse> calculateRewards(List<Transaction> transactions) {
        Map<Long, Map<String, Integer>> customerMonthlyPoints = new HashMap<>();

        for (Transaction transaction : transactions) {
            Long customerId = transaction.getCustomerId();
            String month = transaction.getDate().getMonth().toString() + " " + transaction.getDate().getYear();
            int points = calculatePoints(transaction.getAmount());

            customerMonthlyPoints
                    .computeIfAbsent(customerId, k -> new HashMap<>());

            Map<String, Integer> monthlyPoints = customerMonthlyPoints.get(customerId);

            int prevPoints = monthlyPoints.getOrDefault(month, 0);
            monthlyPoints.put(month, prevPoints + points);
        }

        List<RewardResponse> responses = new ArrayList<>();
        for (var entry : customerMonthlyPoints.entrySet()) {
            Long customerId = entry.getKey();
            Map<String, Integer> monthly = entry.getValue();
            int total = monthly.values().stream().mapToInt(Integer::intValue).sum();

            RewardResponse response = new RewardResponse();
            response.setCustomerId(customerId);
            response.setMonthlyPoints(monthly);
            response.setTotalPoints(total);
            responses.add(response);
        }

        return responses;
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
