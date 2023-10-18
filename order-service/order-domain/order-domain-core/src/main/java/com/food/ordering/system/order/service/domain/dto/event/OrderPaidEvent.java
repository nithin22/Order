package com.food.ordering.system.order.service.domain.dto.event;

import com.food.ordering.system.order.service.domain.dto.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvents {


    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
