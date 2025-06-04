package com.shopping.service;

import com.shopping.exception.CustomerDataNotFoundException;
import com.shopping.exception.InvalidDataException;
import com.shopping.model.RewardResponse;
import com.shopping.model.Transaction;
import com.shopping.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shopping.utils.Constants.CUSTOMER_DATA_NOT_FOUND;
import static com.shopping.utils.Constants.CUSTOMER_ID_INVALID;


/**
 * This service contains method to calculate reward for users
 */
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Calculates Reward Point based on Last 3 month transaction amount
     *
     * @param customerId - fetching transaction based on customerId
     * @return
     */
    @Override
    public RewardResponse calculateRewardForUser(Long customerId) throws Exception {
        if (customerId == null || customerId <= 0) {
            throw new InvalidDataException(CUSTOMER_ID_INVALID);
        }
        LocalDate localDate = LocalDate.now();
        LocalDate startDate = localDate.minusMonths(3);

        //Fetch Last 3 months transactions
        List<Transaction> transactions = transactionRepository
                .findLast3MonthsTransaction(customerId, startDate);

        if (transactions.isEmpty()) {
            throw new CustomerDataNotFoundException(CUSTOMER_DATA_NOT_FOUND);
        }

        Map<String, Integer> customerMonthlyPoints = new HashMap<>();

        //Calculating Monthly points and totalpoints
        for (Transaction transaction : transactions) {
            String month = transaction.getDate().getMonth().toString() + " "
                    + transaction.getDate().getYear();
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
     * Calculate Reward for all customers
     *
     * @return List of RewardResponse
     */
    @Override
    public List<RewardResponse> calculateRewardForAllCustomers() {
        LocalDate localDate = LocalDate.now();
        LocalDate startDate = localDate.minusMonths(3);
        //Fetch All Customer transactions
        List<Transaction> transactions = transactionRepository
                .findLast3MonthsTransactionForAllUsers(startDate);

        if (transactions.isEmpty()) {
            throw new CustomerDataNotFoundException(CUSTOMER_DATA_NOT_FOUND);
        }

        Map<Long, Map<String, Integer>> customerMonthlyPoints = new HashMap<>();

        for (Transaction transaction : transactions) {
            Long customerId = transaction.getCustomerId();
            String month = transaction.getDate().getMonth().toString() + " "
                    + transaction.getDate().getYear();

            int points = calculatePoints(transaction.getAmount());

            //Checking if customerId is present, otherwise adding to customerMonthlyPoints
            customerMonthlyPoints.computeIfAbsent(customerId, k -> new HashMap<>());

            Map<String, Integer> monthlyPoints = customerMonthlyPoints.get(customerId);

            int prevPoints = monthlyPoints.getOrDefault(month, 0);
            monthlyPoints.put(month, prevPoints + points);
        }

        //Creating List of response object
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
     * @param amount to calculate reward points
     *               2 points for every dollar spent over $100
     *               1 point for every dollar spent between $50 and $100
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
