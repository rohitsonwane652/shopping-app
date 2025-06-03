package com.shopping.controller;

import com.shopping.model.RewardResponse;
import com.shopping.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    /**
     * Test for validating correct response
     */
    @Test
    void getRewardForUserTest() throws Exception {
        Long customerId = 1L;
        RewardResponse mockResponse =
                RewardResponse.builder().customerId(1L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build();


        when(rewardService.calculateRewardForUser(customerId)).thenReturn(mockResponse);

        mockMvc.perform(get("/rewards/get-total-reward-points/{customerId}", customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPoints").value(180));
    }

    /**
     * Test for validating exception
     */
    @Test
    void getRewardForUserInternalServerError() throws Exception {
        Long customerId = 1L;

        when(rewardService.calculateRewardForUser(customerId))
                .thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(get("/rewards/get-total-reward-points/{customerId}", customerId))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test for validating getting reward statement of all customers
     */
    @Test
    void getRewardStatementTest() throws Exception {
        Long customerId = 1L;
        List<RewardResponse> mockResponse = Arrays.asList(
                RewardResponse.builder().customerId(1L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build(),
                RewardResponse.builder().customerId(2L)
                        .monthlyPoints(Map.of("APRIL 2025",90,"MAY 2025",90)).totalPoints(180).build()
        );

        when(rewardService.calculateRewardForAllCustomers()).thenReturn(mockResponse);

        mockMvc.perform(get("/rewards/get-total-reward-points")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
