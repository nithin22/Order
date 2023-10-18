package com.food.ordering.system.order.service.domain.dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
public class OrderAddress {

    @NotNull
    @Max(value = 50)
    private final String street;
    @NotNull
    @Max(value = 50)
    private final String postalCode;
    @NotNull
    private final String city;
}