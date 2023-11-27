package com.roomster.roomsterbackend.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDto implements Serializable {
    private String status;
    private String message;
    private String url;
}
