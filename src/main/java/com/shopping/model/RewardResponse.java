package com.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardResponse {

    private Long customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

}

