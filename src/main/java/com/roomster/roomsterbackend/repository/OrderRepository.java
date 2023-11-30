package com.roomster.roomsterbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roomster.roomsterbackend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
		List<Order> findAll() ;


}
