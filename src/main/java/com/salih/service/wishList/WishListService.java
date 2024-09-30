package com.salih.service.wishList;

import com.salih.dto.wishList.WishListRequestDto;
import com.salih.dto.wishList.WishListResponseDto;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.WishListMapper;
import com.salih.model.Product;
import com.salih.model.User;
import com.salih.model.WishList;
import com.salih.repository.ProductRepository;
import com.salih.repository.UserRepository;
import com.salih.repository.WishListRepository;
import com.salih.result.DataResult;
import com.salih.result.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WishListService implements IWishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishListMapper wishListMapper;
    private static final Logger logger = LoggerFactory.getLogger(WishListService.class);

    @Override
    public DataResult<List<WishListResponseDto>> getAllWishLists() {
        List<WishList> wishLists = wishListRepository.findAll();
        if (wishLists.isEmpty()) {
            logger.warn("No wish lists found");
            throw new ResourceNotFoundException("No wish lists found");
        }

        List<WishListResponseDto> wishListDtos = wishLists.stream()
                .map(wishListMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Wish lists listed successfully");
        return new DataResult<>(wishListDtos, Result.showMessage(Result.SUCCESS, "Wish lists listed successfully"));
    }

    @Cacheable(value = "wishlists", key = "#id")
    @Override
    public DataResult<WishListResponseDto> getWishListById(Long id) {
        WishList wishList = wishListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wish list not found with ID: " + id));

        WishListResponseDto wishListDto = wishListMapper.toDto(wishList);
        logger.info("Wish list found with ID: {}", id);
        return new DataResult<>(wishListDto, Result.showMessage(Result.SUCCESS, "Wish list found"));
    }

    @Override
    public Result addWishList(WishListRequestDto wishListRequestDto) {
        User user = userRepository.findById(wishListRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + wishListRequestDto.getUserId()));

        List<Product> products = productRepository.findAllById(wishListRequestDto.getProductIds());

        if (products.isEmpty()) {
            logger.warn("No products found for the wish list");
            throw new ResourceNotFoundException("No products found for the wish list");
        }

        WishList wishList = wishListMapper.toEntity(wishListRequestDto);
        wishList.setUser(user);
        wishList.setProducts(products);

        wishListRepository.save(wishList);
        logger.info("Wish list added successfully with ID: {}", wishList.getId());
        return Result.showMessage(Result.SUCCESS, "Wish list added successfully");
    }

    @CacheEvict(value = "wishlists", key = "#id")
    @Override
    public Result deleteWishList(Long id) {
        WishList wishList = wishListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wish list not found with ID: " + id));

        wishListRepository.delete(wishList);
        logger.info("Wish list deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Wish list deleted successfully");
    }
}
