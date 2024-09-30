package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.order.OrderRequestDto;
import com.salih.dto.order.OrderResponseDto;
import com.salih.dto.order.OrderResponseWithBreadcrumbDto;
import com.salih.model.BreadcrumbItem;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.BreadcrumbService;
import com.salih.service.order.IOrderService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Order Controller", description = "APIs for managing orders")
public class OrderController {

    private final IOrderService orderService;
    private final BreadcrumbService breadcrumbService;

    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @Operation(summary = "Get all orders", description = "Returns a list of all orders.")
    @GetMapping(ApiEndpoints.ORDER_GET_ALL)
    public ResponseEntity<OrderResponseWithBreadcrumbDto> getAllOrders() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb("/orders");

        DataResult<List<OrderResponseDto>> result = orderService.getAllOrders();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new OrderResponseWithBreadcrumbDto(result.getData(), breadcrumbs));
    }

    @Operation(summary = "Get order by ID", description = "Returns a single order based on the provided ID.")
    @GetMapping(ApiEndpoints.ORDER_GET_BY_ID)
    public ResponseEntity<OrderResponseWithBreadcrumbDto> getOrderById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb("/orders/" + id);

        DataResult<OrderResponseDto> result = orderService.getOrderById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new OrderResponseWithBreadcrumbDto(List.of(result.getData()), breadcrumbs));
    }

    @Operation(summary = "Place new order", description = "Creates a new order with the provided data.")
    @PostMapping(ApiEndpoints.ORDER_PLACE)
    public ResponseEntity<Result> placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Cancel order", description = "Cancels an existing order based on the provided ID.")
    @DeleteMapping(ApiEndpoints.ORDER_CANCEL)
    public ResponseEntity<Result> cancelOrder(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = orderService.cancelOrder(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
