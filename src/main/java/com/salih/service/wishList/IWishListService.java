package com.salih.service.wishList;

import com.salih.dto.wishList.WishListRequestDto;
import com.salih.dto.wishList.WishListResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface IWishListService {
    DataResult<List<WishListResponseDto>> getAllWishLists();
    DataResult<WishListResponseDto> getWishListById(Long id);
    Result addWishList(WishListRequestDto wishListRequestDto);
}
