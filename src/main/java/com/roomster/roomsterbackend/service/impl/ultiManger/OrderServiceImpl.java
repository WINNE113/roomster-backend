package com.roomster.roomsterbackend.service.impl.ultiManger;

import com.roomster.roomsterbackend.base.BaseResponse;
import com.roomster.roomsterbackend.dto.order.*;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.OrderEntity;
import com.roomster.roomsterbackend.entity.RoomServiceEntity;
import com.roomster.roomsterbackend.entity.TenantEntity;
import com.roomster.roomsterbackend.service.impl.mailService.MailService;
import com.roomster.roomsterbackend.repository.OrderRepository;
import com.roomster.roomsterbackend.repository.RoomRepository;
import com.roomster.roomsterbackend.repository.TenantRepository;
import com.roomster.roomsterbackend.service.IService.ultiManager.IOrderService;
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
			List<OrderEntity> orderList = orderRepository.findAll();
			orderList.sort(Comparator.comparing(OrderEntity::getPaymentDate,Comparator.reverseOrder()));
			List<OrderPaymentDto> orderPaymentDtos = new ArrayList<OrderPaymentDto>();
			for (OrderEntity order : orderList) {
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
				Optional<OrderEntity> order = this.orderRepository.findById(idL);
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
				Optional<OrderEntity> order = this.orderRepository.findById(idL);
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
		OrderEntity oldOrder = null;
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(order.getOrderId())) {
				Long idL = Long.parseLong(order.getOrderId());
				Optional<OrderEntity> Order = this.orderRepository.findById(idL);
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
			List<PaymentByMonthDto> paymentByMonthDtoList = result.stream()
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
	public ResponseEntity<?> checkUpdateOrAddFromMonth(String id, OrderEntity order) {

		List<OrderEntity> orderCheck = orderRepository.findAll();
		LocalDate currentDate = LocalDate.now();
		int currentMonth = currentDate.getMonth().getValue() - 1;
		Optional<OrderEntity> orderOptional =	orderRepository.findOrderForRoomInCurrentMonth(order.getRoomId());

		System.out.println("orderExists " + orderOptional.isPresent());
		if (orderOptional.isPresent()) {
			return updateOrderAfterCheck(orderOptional.get().getOrderId().toString(), order);
		}

		return createOrderAfterCheck(order);
	}

	public ResponseEntity<?> createOrderAfterCheck(OrderEntity order) {
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
			for (RoomServiceEntity roomServicePrice : roomService.getServices()) {
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

	public ResponseEntity<?> updateOrderAfterCheck(String id, OrderEntity order) {
		System.out.println("UPDATE");

		ResponseEntity<?> responseEntity;
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal priceService = BigDecimal.ZERO;
		BigDecimal priceRoom = BigDecimal.ZERO;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<OrderEntity> existingOrderOptional = orderRepository.findById(idL);
				if (existingOrderOptional.isPresent()) {
					OrderEntity existingOrder = existingOrderOptional.get();
					Optional<InforRoomEntity> room = roomRepository.findById(order.getRoomId());
					existingOrder.setElectricity(order.getElectricity());
					existingOrder.setWater(order.getWater());
					existingOrder.setStatusPayment("N");
					LocalDate currentDate = LocalDate.now();
					existingOrder.setPaymentDate(Date.valueOf(currentDate));
					InforRoomEntity roomService = room.get();
					priceRoom = priceRoom.add(roomService.getPrice());
					for (RoomServiceEntity roomServicePrice : roomService.getServices()) {
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
		List<OrderEntity> listOrder = orderRepository.findAll();
		LocalDate currentDate = LocalDate.now();
		int currentMonth = currentDate.getMonth().getValue() - 1;
		List<OrderEntity> listOrderResult = listOrder.stream()
				.filter(o -> (o.getRoomId().toString().equals(roomId) && (o.getStatusPayment().trim().equals("N") || o.getStatusPayment().trim().equals("P"))))
				.toList();
		for (OrderEntity order : listOrderResult) {
			List<TenantEntity> listTenant = tenantRepository.findByRoomId(order.getRoomId());
			for (TenantEntity tenant : listTenant) {
				mailService.sendSimpleEmail(tenant, "Hóa đơn thánh toán phòng trọ tháng : " + currentMonth , "" , tenant.getName() ,order);
			}
		}
		return new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_SEND_MAIL_SUCCESS),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateOrderPayment(String id, OrderStatusPaymentDto order) {
		ResponseEntity<?> responseEntity;
		try{
			Long idL = Long.parseLong(id);
			Optional<OrderEntity> orderEntityOptional = this.orderRepository.findById(idL);
			if(orderEntityOptional.isPresent()) {
				OrderEntity orderEntity = orderEntityOptional.get();
				orderEntity.setTotalPayment(BigDecimal.valueOf(Long.parseLong(order.getBillNumber())));
				if (orderEntity.getTotalPayment().compareTo(orderEntity.getTotal()) == 0) {
					orderEntity.setStatusPayment("Y");
				} else if (orderEntity.getTotalPayment().compareTo(orderEntity.getTotal()) < 0) {
					orderEntity.setStatusPayment("P");
				} else if (orderEntity.getTotalPayment().compareTo(BigDecimal.ZERO) == 0) {
					orderEntity.setStatusPayment("N");
				}
				this.orderRepository.save(orderEntity);
				responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_UPDATE_SUCCESS),
						HttpStatus.OK);
			}else{
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch (Exception e){
			responseEntity = new ResponseEntity<>(BaseResponse.error(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> getOrderBillById(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<OrderEntity> orderEntityOptional = this.orderRepository.findById(idL);
				if (orderEntityOptional.isPresent()) {
					OrderEntity order = orderEntityOptional.get();
					OrderBillDTO orderBillDTO = new OrderBillDTO();
					orderBillDTO.setOrderId(order.getOrderId().toString());
					orderBillDTO.setHouseName(order.getRoom().getHouse().getHouseName());
					orderBillDTO.setHouseAddress(order.getRoom().getHouse().getAddress());
					orderBillDTO.setRoomNumber(order.getRoom().getNumberRoom() + "");
					orderBillDTO.setServicePrice(order.getService().toString());
					orderBillDTO.setElectricPrice(order.getElectricity().multiply(order.getRoom().getElectricityPrice()).toString());
					orderBillDTO.setWaterPrice(order.getWater().multiply(order.getRoom().getWaterPrice()).toString());
					orderBillDTO.setTotalPayment(order.getTotalPayment().toString());
					orderBillDTO.setTotalPrice(order.getTotal().toString());
					orderBillDTO.setDatePayment(order.getPaymentDate().toString());
					responseEntity = new ResponseEntity<>(orderBillDTO, HttpStatus.OK);
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
