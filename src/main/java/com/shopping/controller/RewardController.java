package com.shopping.controller;

import com.shopping.model.RewardResponse;
import com.shopping.model.Transaction;
import com.shopping.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/calculateReward")
    public ResponseEntity<List<RewardResponse>> getRewards(@RequestBody List<Transaction> transactions) {
        try {
            List<RewardResponse> rewards = rewardService.calculateRewards(transactions);
//            throw new Exception("Getting Error");
            return ResponseEntity.ok(rewards);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}