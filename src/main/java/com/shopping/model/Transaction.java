package com.shopping.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private Long customerId;
    private double amount;
    private LocalDate date;

}
