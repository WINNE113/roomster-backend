package com.roomster.roomsterbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roomster.roomsterbackend.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	List<OrderEntity> findAll();
	@Query("SELECT MONTH(o.paymentDate) AS month, SUM(o.total) AS total FROM OrderEntity o WHERE o.statusPayment = 'Y' GROUP BY MONTH(o.paymentDate) ORDER BY MONTH(o.paymentDate)")
	List<Object[]> getTotalPaymentByMonth();

	@Query("SELECT o FROM OrderEntity o " +
			"WHERE o.roomId = :roomId " +
			"AND FUNCTION('MONTH', o.paymentDate) = FUNCTION('MONTH', CURRENT_DATE)")
	Optional<OrderEntity> findOrderForRoomInCurrentMonth(@Param("roomId") Long roomId);

}
