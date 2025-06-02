package com.shopping.service;

import com.shopping.model.RewardResponse;

import java.util.List;

public interface RewardService {
    RewardResponse calculateRewardForUser(Long customerId) throws Exception;
    List<RewardResponse> calculateRewardForAllCustomers();
}
