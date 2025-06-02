package com.shopping.controller;

import com.shopping.model.Response;
import com.shopping.utils.LogMessage;
import com.shopping.model.RewardResponse;
import com.shopping.service.RewardService;
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
     * @param customerId
     * @return
     */
    @GetMapping("/user/{customerId}")
    public ResponseEntity<Response<RewardResponse>> getRewardForUser(@PathVariable Long customerId) throws Exception{
        Response<RewardResponse> response = new Response<>();

        try{
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            LogMessage.startLog(this.getClass().getName(), methodName);
            RewardResponse reward = rewardService.calculateRewardForUser(customerId);
            LogMessage.infoLog("Successfully calculated reward for customerId :" + customerId);
            response.setData(reward);
            response.setMessage("Successfully fetched reward points for customerId:" + customerId);
            response.setTotalRecords(1L);
            LogMessage.endLog(this.getClass().getName(), methodName);
        } catch (Exception e){
            LogMessage.logStackTrace(e.getClass().getName(), e);
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    /**
     * api to get all customers reward points
     * @return
     */
    @GetMapping("/get-reward-statement")
    public ResponseEntity<Response<List<RewardResponse>>> getRewardStatement(){
        Response<List<RewardResponse>> response = new Response<>();
        try{
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            LogMessage.startLog(this.getClass().getName(), methodName);
            List<RewardResponse> reward = rewardService.calculateRewardForAllCustomers();
            LogMessage.infoLog("Successfully generated statement for all customer reward");

            response.setData(reward);
            response.setMessage("Successfully fetched reward points for all customers");
            response.setTotalRecords((long)reward.size());
            LogMessage.endLog(this.getClass().getName(), methodName);
        } catch (Exception e){
            LogMessage.logStackTrace(e.getClass().getName(), e);
            throw e;
        }
        return ResponseEntity.ok(response);
    }

}
