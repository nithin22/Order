package com.food.ordering.system.order.service.domain.dto;

import com.food.ordering.system.order.service.domain.dto.entity.Order;
import com.food.ordering.system.order.service.domain.dto.entity.Restaurant;
import com.food.ordering.system.order.service.domain.dto.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.dto.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.dto.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order,List<String> failureMessages);
}
