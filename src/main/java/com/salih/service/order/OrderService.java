package com.salih.service.order;

import com.salih.dto.order.OrderRequestDto;
import com.salih.dto.order.OrderResponseDto;
import com.salih.exception.OutOfStockException;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.OrderMapper;
import com.salih.model.Order;
import com.salih.model.Product;
import com.salih.model.User;
import com.salih.repository.OrderRepository;
import com.salih.repository.ProductRepository;
import com.salih.repository.UserRepository;
import com.salih.result.DataResult;
import com.salih.result.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public DataResult<List<OrderResponseDto>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            logger.warn("No orders found");
            throw new ResourceNotFoundException("No orders found");
        }

        List<OrderResponseDto> orderDtos = orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Orders listed successfully");
        return new DataResult<>(orderDtos, Result.showMessage(Result.SUCCESS, "Orders listed successfully"));
    }

    @Cacheable(value = "orders", key = "#id")
    @Override
    public DataResult<OrderResponseDto> getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        OrderResponseDto orderDto = orderMapper.toDto(order);
        logger.info("Order found with ID: {}", id);
        return new DataResult<>(orderDto, Result.showMessage(Result.SUCCESS, "Order found"));
    }

    @Override
    public Result placeOrder(OrderRequestDto orderRequestDto) {
        User buyer = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with ID: " + orderRequestDto.getUserId()));

        List<Product> products = productRepository.findAllById(orderRequestDto.getProductIds());

        if (products.isEmpty()) {
            logger.warn("No products found for the order");
            throw new ResourceNotFoundException("No products found for the order");
        }

        // Ürün stoklarını kontrol et
        for (Product product : products) {
            if (product.getStockQuantity() < 1) {
                logger.error("Product {} is out of stock", product.getName());
                throw new OutOfStockException("Product " + product.getName() + " is out of stock");
            }
        }

        Order order = orderMapper.toEntity(orderRequestDto);
        order.setUser(buyer);
        order.setProducts(products);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        logger.info("Order placed successfully with ID: {}", order.getId());
        return Result.showMessage(Result.SUCCESS, "Order placed successfully");
    }

    @CacheEvict(value = "orders", key = "#id")
    @Override
    public Result cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        orderRepository.delete(order);
        logger.info("Order deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Order deleted successfully");
    }

    @Override
    public Result updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));

        orderRepository.save(order);
        logger.info("Order status updated successfully with ID: {}", id);
        return Result.SUCCESS;
    }
}
