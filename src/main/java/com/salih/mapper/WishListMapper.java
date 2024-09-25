package com.salih.mapper;

import com.salih.dto.wishList.WishListRequestDto;
import com.salih.dto.wishList.WishListResponseDto;
import com.salih.model.Product;
import com.salih.model.WishList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WishListMapper {

    @Mapping(source = "user.id", target = "userId")
    WishList toEntity(WishListRequestDto wishListRequestDto);

    @Mapping(source = "user.name", target = "userName")
    @Mapping(target = "productNames", source = "products", qualifiedByName = "mapProductNames")
    WishListResponseDto toDto(WishList wishList);

    @Named("mapProductNames")
    default List<String> mapProductNames(List<Product> products) {
        return products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }
}
