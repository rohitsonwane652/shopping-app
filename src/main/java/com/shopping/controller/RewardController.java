package com.shopping.controller;

import com.shopping.model.RewardResponse;
import com.shopping.service.RewardService;
import com.shopping.utils.LogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller contains apis for getting reward points
 */
@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    /**
     * api to get last 3 month transaction reward based on customerId
     *
     * @param customerId
     * @return
     */
    @GetMapping("/get-total-reward-points/{customerId}")
    public ResponseEntity<RewardResponse> getRewardForUser(@PathVariable Long customerId) throws Exception {
        RewardResponse response;
        try {
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            LogMessage.startLog(this.getClass().getName(), methodName);
            response = rewardService.calculateRewardForUser(customerId);
            LogMessage.infoLog("Successfully calculated reward for customerId :" + customerId);
            LogMessage.endLog(this.getClass().getName(), methodName);
        } catch (Exception e) {
            LogMessage.logStackTrace(e.getClass().getName(), e);
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    /**
     * api to get all customers reward points
     *
     * @return
     */
    @GetMapping("/get-total-reward-points")
    public ResponseEntity<List<RewardResponse>> getRewardStatement() {
        List<RewardResponse> response;
        try {
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            LogMessage.startLog(this.getClass().getName(), methodName);
            response = rewardService.calculateRewardForAllCustomers();
            LogMessage.infoLog("Successfully generated statement for all customer reward");
            LogMessage.endLog(this.getClass().getName(), methodName);
        } catch (Exception e) {
            LogMessage.logStackTrace(e.getClass().getName(), e);
            throw e;
        }
        return ResponseEntity.ok(response);
    }

}
