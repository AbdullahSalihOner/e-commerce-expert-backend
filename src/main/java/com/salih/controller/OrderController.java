package com.salih.controller;

import com.salih.dto.order.OrderRequestDto;
import com.salih.dto.order.OrderResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.order.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<DataResult<List<OrderResponseDto>>> getAllOrders() {
        DataResult<List<OrderResponseDto>> result = orderService.getAllOrders();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        DataResult<OrderResponseDto> result = orderService.getOrderById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping("/place")
    public ResponseEntity<Result> placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Result result = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }
}
