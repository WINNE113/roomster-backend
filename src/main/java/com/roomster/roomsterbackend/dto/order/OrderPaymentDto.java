package com.roomster.roomsterbackend.dto.order;

import java.math.BigDecimal;
import java.util.List;

import com.roomster.roomsterbackend.entity.Order;
import com.roomster.roomsterbackend.entity.ServiceHouse;

import lombok.Data;

@Data
public class OrderPaymentDto extends Order {
	
  private BigDecimal priceService; 
  
  private List<ServiceHouse> serviceHouses;	
  
}
