package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.dto.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.roomster.roomsterbackend.entity.Order;
import com.roomster.roomsterbackend.service.IService.IOrderService;

@RestController
@CrossOrigin("*")
@RequestMapping("/room-master/order")
public class OrderController {
	
	@Autowired
    IOrderService orderService;
	
	@GetMapping()
	public ResponseEntity<?> getAllorderService() {
		return orderService.findAll();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getorderServiceById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public ResponseEntity<?> createorderService(@RequestBody Order order) {
    	System.out.println("123");
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateorderService(@PathVariable String id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteorderService(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }

    @PutMapping("water-electric")
    public ResponseEntity<?> updateorderService(@RequestBody OrderDTO order) {
        return orderService.updateOrderWaterElectric(order);
    }


}
