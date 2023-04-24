package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserInMonthDto {
    private int month;
    private BigDecimal totalDeposit;
    private BigDecimal totalRent;
    private BigDecimal total;

}