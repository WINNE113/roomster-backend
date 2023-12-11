package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.order.OrderDTO;
import com.roomster.roomsterbackend.dto.order.OrderPaymentDto;
import com.roomster.roomsterbackend.dto.order.PaymentByMonthDto;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.Order;
import com.roomster.roomsterbackend.entity.RoomService;
import com.roomster.roomsterbackend.entity.Tenant;
import com.roomster.roomsterbackend.mailService.MailService;
import com.roomster.roomsterbackend.repository.OrderRepository;
import com.roomster.roomsterbackend.repository.RoomRepository;
import com.roomster.roomsterbackend.repository.TenantRepository;
import com.roomster.roomsterbackend.service.IService.IOrderService;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import com.roomster.roomsterbackend.util.validator.ValidatorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	OrderRepository orderRepository;
	
	
	@Autowired
	TenantRepository tenantRepository;
	
	@Autowired
	MailService mailService;

	@Override
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> responseEntity;
		try {
			List<Order> orderList = orderRepository.findAll();
			orderList.sort(Comparator.comparing(Order::getPaymentDate,Comparator.reverseOrder()));
			List<OrderPaymentDto> orderPaymentDtos = new ArrayList<OrderPaymentDto>();
			for (Order order : orderList) {
				OrderPaymentDto paymentDto = new OrderPaymentDto();
				Long roomId = order.getRoomId();
				Optional<InforRoomEntity> inforRoomEntity = roomRepository.findById(roomId);
				if (inforRoomEntity.isPresent()) {
					InforRoomEntity room = inforRoomEntity.get();
					BeanUtils.copyProperties(order, paymentDto);
					paymentDto.setPriceService(order.getService());
					paymentDto.setElectricity(paymentDto.getElectricity().multiply(room.getElectricityPrice()));
					paymentDto.setWater(paymentDto.getWater().multiply(room.getWaterPrice()));
					paymentDto.setPriceRoom(room.getPrice());
					orderPaymentDtos.add(paymentDto);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			responseEntity = new ResponseEntity<>(orderPaymentDtos, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> getOrderById(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<Order> order = this.orderRepository.findById(idL);
				if (order.isPresent()) {
					responseEntity = new ResponseEntity<>(order.get(), HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> deleteOrder(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<Order> order = this.orderRepository.findById(idL);
				if (order.isPresent()) {
					this.orderRepository.deleteById(idL);
					responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS),
							HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> updateOrderWaterElectric(OrderDTO order) {
		Order oldOrder = null;
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(order.getOrderId())) {
				Long idL = Long.parseLong(order.getOrderId());
				Optional<Order> Order = this.orderRepository.findById(idL);
				if (Order.isPresent()) {
					oldOrder.setElectricity(BigDecimal.valueOf(Double.parseDouble(order.getElectric())));
					oldOrder.setWater(BigDecimal.valueOf(Double.parseDouble(order.getWater())));
					oldOrder = this.orderRepository.save(oldOrder);
					responseEntity = new ResponseEntity<>(oldOrder, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> getTotalPaymentByMonth() {
		ResponseEntity<?> responseEntity;
		try{
			List<Object[]> result = orderRepository.getTotalPaymentByMonth();
			List<PaymentByMonthDto> paymentByMonthDtoList =  result.stream()
					.map(row -> new PaymentByMonthDto((Integer) row[0], (BigDecimal) row[1]))
					.toList();
			responseEntity = new ResponseEntity<>(paymentByMonthDtoList, HttpStatus.OK);
		}
		catch (Exception e){
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> checkUpdateOrAddFromMonth(String id, Order order) {

		List<Order> orderCheck = orderRepository.findAll();
		LocalDate currentDate = LocalDate.now();
		int currentMonth = currentDate.getMonth().getValue() - 1;
		Optional<Order> orderOptional =	orderRepository.findOrderForRoomInCurrentMonth(order.getRoomId());

		System.out.println("orderExists " + orderOptional.isPresent());
		if (orderOptional.isPresent()) {
			return updateOrderAfterCheck(orderOptional.get().getOrderId().toString(), order);
		}

		return createOrderAfterCheck(order);
	}

	public ResponseEntity<?> createOrderAfterCheck(Order order) {
		System.out.println("ADD");
		ResponseEntity<?> responseEntity;
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal priceService = BigDecimal.ZERO;
		BigDecimal priceRoom = BigDecimal.ZERO;
		try {
			// check room
			Long idRoom = order.getRoomId();
			Optional<InforRoomEntity> room = roomRepository.findById(idRoom);
			InforRoomEntity roomService = room.get();
			priceRoom = priceRoom.add(roomService.getPrice());
			for (RoomService roomServicePrice : roomService.getServices()) {
				priceService = priceService.add(roomServicePrice.getServiceHouse().getServicePrice());
			}
			order.setService(priceService);
			if (room.isPresent()) {
				// validate room
				if (order.getWater() != null && order.getElectricity() != null) {
					price = room.get().getElectricityPrice().multiply(order.getElectricity())
							.add(room.get().getWaterPrice().multiply(order.getWater()));

					order.setTotal(price.add(priceService.add(priceRoom)));
					order.setStatusPayment("N");
					LocalDate currentDate = LocalDate.now();
					order.setPaymentDate(Date.valueOf(currentDate));
				}
				order = orderRepository.save(order);
				responseEntity = new ResponseEntity<>(order, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROOM_NOT_FOUND),
						HttpStatus.METHOD_NOT_ALLOWED);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	public ResponseEntity<?> updateOrderAfterCheck(String id, Order order) {
		System.out.println("UPDATE");

		ResponseEntity<?> responseEntity;
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal priceService = BigDecimal.ZERO;
		BigDecimal priceRoom = BigDecimal.ZERO;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<Order> existingOrderOptional = orderRepository.findById(idL);
				if (existingOrderOptional.isPresent()) {
					Order existingOrder = existingOrderOptional.get();
					Optional<InforRoomEntity> room = roomRepository.findById(order.getRoomId());
					existingOrder.setOrderId(idL);
					existingOrder.setRoomId(order.getRoomId());
					existingOrder.setRoom(room.get());
					existingOrder.setElectricity(order.getElectricity());
					existingOrder.setWater(order.getWater());
					existingOrder.setPaymentDate(order.getPaymentDate());
					existingOrder.setStatusPayment(order.getStatusPayment());

					InforRoomEntity roomService = room.get();
					priceRoom = priceRoom.add(roomService.getPrice());
					for (RoomService roomServicePrice : roomService.getServices()) {
						priceService = priceService.add(roomServicePrice.getServiceHouse().getServicePrice());
					}
					existingOrder.setService(priceService);
					if (room.isPresent()) {
						// validate room
						if (order.getWater() != null && order.getElectricity() != null) {
							price = room.get().getElectricityPrice().multiply(order.getElectricity())
									.add(room.get().getWaterPrice().multiply(order.getWater()));

							existingOrder.setTotal(price.add(priceService.add(priceRoom)));
						}

						existingOrder = orderRepository.save(existingOrder);

						return new ResponseEntity<>(existingOrder, HttpStatus.OK);
					}
					responseEntity = new ResponseEntity<>(existingOrder, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> sendMailPayment(String roomId) {
		ResponseEntity<?> responseEntity;
		List<Order> listOrder = orderRepository.findAll();
		LocalDate currentDate = LocalDate.now();
		int currentMonth = currentDate.getMonth().getValue() - 1;
		List<Order> listOrderResult = listOrder.stream()
				.filter(o -> (o.getRoomId().toString().equals(roomId) && o.getStatusPayment().trim().equals("N")))
				.toList();
		for (Order order : listOrderResult) {
			List<Tenant> listTenant = tenantRepository.findByRoomId(order.getRoomId());
			for (Tenant tenant : listTenant) {
				mailService.sendSimpleEmail(tenant, "Hóa đơn thánh toán phòng trọ tháng : " + currentMonth , "" , tenant.getName() ,order);
			}
		}
		return null;
	}
}
