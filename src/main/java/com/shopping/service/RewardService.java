package com.shopping.service;

import com.shopping.model.RewardResponse;

public interface RewardService {
    RewardResponse calculateRewardForUser(Long customerId);
}
