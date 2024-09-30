package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.order.OrderRequestDto;
import com.salih.dto.order.OrderResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.order.IOrderService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.ORDER_BASE)
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    // Rate limiting bucket: 100 requests per minute
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @GetMapping(ApiEndpoints.ORDER_GET_ALL)
    public ResponseEntity<DataResult<List<OrderResponseDto>>> getAllOrders() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<OrderResponseDto>> result = orderService.getAllOrders();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping(ApiEndpoints.ORDER_GET_BY_ID)
    public ResponseEntity<DataResult<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<OrderResponseDto> result = orderService.getOrderById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping(ApiEndpoints.ORDER_PLACE)
    public ResponseEntity<Result> placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping(ApiEndpoints.ORDER_CANCEL)
    public ResponseEntity<Result> cancelOrder(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = orderService.cancelOrder(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
