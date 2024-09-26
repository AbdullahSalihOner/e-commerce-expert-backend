package com.salih.controller;

import com.salih.dto.wishList.WishListRequestDto;
import com.salih.dto.wishList.WishListResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.wishList.IWishListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
@RequiredArgsConstructor
public class WishListController {

    private final IWishListService wishListService;

    @GetMapping("/all")
    public ResponseEntity<DataResult<List<WishListResponseDto>>> getAllWishLists() {
        DataResult<List<WishListResponseDto>> result = wishListService.getAllWishLists();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<WishListResponseDto>> getWishListById(@PathVariable Long id) {
        DataResult<WishListResponseDto> result = wishListService.getWishListById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addWishList(@RequestBody @Valid WishListRequestDto wishListRequestDto) {
        Result result = wishListService.addWishList(wishListRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }
}
