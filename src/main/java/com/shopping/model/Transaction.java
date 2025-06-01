package com.shopping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id
    private Long orderId;
    private Long itemId;
    private Long customerId;
    private double amount;
    private LocalDate date;

}
