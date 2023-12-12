package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.payment.PaymentDtoMapper;
import com.roomster.roomsterbackend.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PaymentMapperDecorator implements PaymentMapper {

    @Autowired
    @Qualifier("delegate")
    private PaymentMapper delegate;

    @Override
    public PaymentDtoMapper entityToDto(PaymentEntity paymentEntity) {
        PaymentDtoMapper paymentDtoMapper = delegate.entityToDto(paymentEntity);
        paymentDtoMapper.setPaymentDestinationsName(paymentEntity.getPaymentDestinations().getId());
        return paymentDtoMapper;
    }
}
