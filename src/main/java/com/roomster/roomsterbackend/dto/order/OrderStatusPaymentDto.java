package com.roomster.roomsterbackend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusPaymentDto {
    private String date;
    private String billNumber;
}
