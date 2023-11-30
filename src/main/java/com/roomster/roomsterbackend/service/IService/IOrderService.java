package com.roomster.roomsterbackend.service.IService;


import com.roomster.roomsterbackend.dto.order.OrderDTO;
import com.roomster.roomsterbackend.entity.Order;
import org.springframework.http.ResponseEntity;

public interface IOrderService {

	ResponseEntity<?> findAll();

	ResponseEntity<?> getOrderById(String id);

	ResponseEntity<?> createOrder(Order order);

	ResponseEntity<?> updateOrder(String id, Order order);

	ResponseEntity<?> deleteOrder(String id);

	ResponseEntity<?> updateOrderWaterElectric(OrderDTO order);
}
