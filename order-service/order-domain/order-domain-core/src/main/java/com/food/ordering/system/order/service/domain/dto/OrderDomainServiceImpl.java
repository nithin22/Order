package com.food.ordering.system.order.service.domain.dto;

import com.food.ordering.system.order.service.domain.dto.entity.Order;
import com.food.ordering.system.order.service.domain.dto.entity.Product;
import com.food.ordering.system.order.service.domain.dto.entity.Restaurant;
import com.food.ordering.system.order.service.domain.dto.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.dto.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.dto.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.dto.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order,restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated",order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("CST")));
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct->{
                    Product currentProduct=orderItem.getProduct();
                    if(currentProduct.equals(restaurantProduct)){
                        currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),
                                restaurantProduct.getPrice());
                    }
                }
                ));
    }

    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive())
        {
            throw new OrderDomainException("Restaurent with id: "+restaurant.getId().getValue()+" is Not Active");
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with  id :{} is paid",order.getId().getValue());
        return new OrderPaidEvent(order,ZonedDateTime.now(ZoneId.of("CST")));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved",order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelled for order id: {}",order.getId().getValue());
        return new OrderCancelledEvent(order,ZonedDateTime.now(ZoneId.of("CST")));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled",order.getId().getValue());
    }
}
