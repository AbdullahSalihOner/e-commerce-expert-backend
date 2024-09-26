package com.salih.service.order;

import com.salih.dto.order.OrderRequestDto;
import com.salih.dto.order.OrderResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface IOrderService {
    DataResult<List<OrderResponseDto>> getAllOrders();
    DataResult<OrderResponseDto> getOrderById(Long id);
    Result placeOrder(OrderRequestDto orderRequestDto);
}
