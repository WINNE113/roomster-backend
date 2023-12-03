package com.roomster.roomsterbackend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.order.OrderDTO;
import com.roomster.roomsterbackend.dto.order.OrderPaymentDto;
import com.roomster.roomsterbackend.repository.RoomRepository;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import com.roomster.roomsterbackend.util.validator.ValidatorUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.Order;
import com.roomster.roomsterbackend.entity.RoomService;
import com.roomster.roomsterbackend.repository.OrderRepository;
import com.roomster.roomsterbackend.service.IService.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	OrderRepository orderRepository;

	@Override
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> responseEntity;
		try {
			List<Order> inforHouseEntityList = orderRepository.findAll();

			List<OrderPaymentDto> orderPaymentDtos = new ArrayList<OrderPaymentDto>();

			BigDecimal priceServicePayment = BigDecimal.ZERO;
			for (Order order : inforHouseEntityList) {
				OrderPaymentDto paymentDto = new OrderPaymentDto();
				Long roomId = order.getRoomId();
				Optional<InforRoomEntity> inforRoomEntity = roomRepository.findById(roomId);
				if (inforRoomEntity.isPresent()) {
					InforRoomEntity room = inforRoomEntity.get();
					for (RoomService roomService : room.getServices()) {	
						priceServicePayment = priceServicePayment.add(roomService.getServiceHouse().getServicePrice());
					}
					BeanUtils.copyProperties(order, paymentDto);
					if (priceServicePayment != null) {
						paymentDto.setPriceService(priceServicePayment);
					}

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
	public ResponseEntity<?> createOrder(Order order) {
		ResponseEntity<?> responseEntity;
		BigDecimal siu = BigDecimal.ZERO;
		BigDecimal siu2 = BigDecimal.ZERO;
		try {
			// check room
			Long idRoom = order.getRoomId();
			Optional<InforRoomEntity> room = roomRepository.findById(idRoom);
			InforRoomEntity roomService = room.get();
			for (RoomService roomServicePrice : roomService.getServices()) {
			     siu2 = siu2.add(roomServicePrice.getServiceHouse().getServicePrice());
			}
			if (room.isPresent()) {
				// validate room
				if (order.getWater() != null && order.getElectricity() != null) {
					siu = room.get().getElectricityPrice().multiply(order.getElectricity())
							.add(room.get().getWaterPrice().multiply(order.getWater()));
					System.out.println(siu + " after " + siu2);
					order.setTotal(siu.add(siu2));
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

	@Override
	public ResponseEntity<?> updateOrder(String id, Order order) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<Order> existingOrderOptional = orderRepository.findById(idL);
				if (existingOrderOptional.isPresent()) {
					Order existingOrder = existingOrderOptional.get();
					Optional<InforRoomEntity> room = roomRepository.findById(order.getRoomId());
					existingOrder.setRoomId(order.getRoomId());
					existingOrder.setRoom(order.getRoom());
					existingOrder.setElectricity(order.getElectricity());
					existingOrder.setWater(order.getWater());
					existingOrder.setStatusPayment(order.getStatusPayment());
					order.setTotal(room.get().getElectricityPrice().multiply(order.getElectricity())
							.add(room.get().getWaterPrice().multiply(order.getWater())));
					order = orderRepository.save(existingOrder);
					responseEntity = new ResponseEntity<>(order, HttpStatus.OK);
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
}
