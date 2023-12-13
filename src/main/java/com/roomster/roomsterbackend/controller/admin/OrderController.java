package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.dto.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.roomster.roomsterbackend.entity.OrderEntity;
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

    @GetMapping("/status")
    public ResponseEntity<?> getTotalPaymentByMonth() {
        return orderService.getTotalPaymentByMonth();
    }

    @PostMapping("/{id}")
	public ResponseEntity<?> createorderService(@PathVariable String id, @RequestBody OrderEntity order) {
        System.out.println("HERE WE GO");
		return orderService.checkUpdateOrAddFromMonth(id, order);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteorderService(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }

    @PutMapping("water-electric")
    public ResponseEntity<?> updateorderService(@RequestBody OrderDTO order) {
        return orderService.updateOrderWaterElectric(order);
    }
	
	@PostMapping("mail-payment/{roomId}")
	public ResponseEntity<?> sendMail(@PathVariable String roomId) {
		return orderService.sendMailPayment(roomId);
	}

}
