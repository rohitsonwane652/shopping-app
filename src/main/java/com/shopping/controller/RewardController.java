package com.shopping.controller;

import com.shopping.utils.LogMessage;
import com.shopping.model.RewardResponse;
import com.shopping.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    /**
     * api to get last 3 month transaction reward based on customerId
     * @param customerId
     * @return
     */
    @GetMapping("/user/{customerId}")
    public ResponseEntity<RewardResponse> getRewardForUser(@PathVariable Long customerId){
        LogMessage.infoLog("Request to calculate Reward For customerId:" + customerId);
        try{
            RewardResponse reward = rewardService.calculateRewardForUser(customerId);
            LogMessage.infoLog("Successfully calculated reward for customerId :" + reward);
            return ResponseEntity.ok(reward);
        } catch (Exception e){
            LogMessage.errorLog("Error occured for calculating reward for customerId: " + customerId);
            throw e;
        }
    }

    /**
     * api to get all customers reward points
     * @return
     */
    @GetMapping("/get-reward-statement")
    public ResponseEntity<List<RewardResponse>> getRewardStatement(){
        LogMessage.infoLog("Request to get all customers reward for year ");
        try{
            List<RewardResponse> reward = rewardService.calculateRewardForAllCustomers();
            LogMessage.infoLog("Successfully generated statement for all customer reward");
            return ResponseEntity.ok(reward);
        } catch (Exception e){
            LogMessage.errorLog("Error occured for calculating reward ");
            throw e;
        }
    }
}
