package com.roomster.roomsterbackend.repository.payment;

import com.roomster.roomsterbackend.entity.PaymentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    List<PaymentEntity> findAllByUserPayment_IdOrderByCreatedDate(Long userId , Pageable pageable);
}
