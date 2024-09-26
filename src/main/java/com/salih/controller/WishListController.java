package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.wishList.WishListRequestDto;
import com.salih.dto.wishList.WishListResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.wishList.IWishListService;
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
@RequestMapping(ApiEndpoints.WISHLIST_BASE)
@RequiredArgsConstructor
public class WishListController {

    private final IWishListService wishListService;

    // Rate limiting bucket: 100 requests per minute
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @GetMapping(ApiEndpoints.WISHLIST_GET_ALL)
    public ResponseEntity<DataResult<List<WishListResponseDto>>> getAllWishLists() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<WishListResponseDto>> result = wishListService.getAllWishLists();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping(ApiEndpoints.WISHLIST_GET_BY_USER_ID)
    public ResponseEntity<DataResult<WishListResponseDto>> getWishListById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<WishListResponseDto> result = wishListService.getWishListById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping(ApiEndpoints.WISHLIST_ADD)
    public ResponseEntity<Result> addWishList(@RequestBody @Valid WishListRequestDto wishListRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = wishListService.addWishList(wishListRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }
}
