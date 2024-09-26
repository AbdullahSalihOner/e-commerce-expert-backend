package com.salih.mapper;

import com.salih.dto.order.OrderRequestDto;
import com.salih.dto.order.OrderResponseDto;
import com.salih.model.Order;
import com.salih.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "userId", target = "user.id") // DTO'daki userId'den Order entity'sindeki user.id'ye
    Order toEntity(OrderRequestDto orderRequestDto);

    @Mapping(source = "user.name", target = "userName") // Entity'deki user.name'den DTO'daki userName'e
    @Mapping(target = "productNames", source = "products", qualifiedByName = "mapProductNames")
    OrderResponseDto toDto(Order order);

    @Named("mapProductNames")
    default List<String> mapProductNames(List<Product> products) {
        return products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }
}
