package com.roomster.roomsterbackend.service.IService;


import com.roomster.roomsterbackend.dto.order.OrderDTO;
import com.roomster.roomsterbackend.entity.Order;
import org.springframework.http.ResponseEntity;

public interface IOrderService {

	ResponseEntity<?> findAll();

	ResponseEntity<?> getOrderById(String id);

	ResponseEntity<?> deleteOrder(String id);

	ResponseEntity<?> updateOrderWaterElectric(OrderDTO order);

	ResponseEntity<?> getTotalPaymentByMonth();
	
	ResponseEntity<?> checkUpdateOrAddFromMonth(String id, Order order);
	
	ResponseEntity<?> sendMailPayment(String roomId);
}
