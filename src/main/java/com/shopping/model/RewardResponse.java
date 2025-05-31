package com.shopping.model;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RewardResponse {

    private Long customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

}

