package com.salih.dto.wishList;

import com.salih.model.BreadcrumbItem;

import java.util.List;

public class WishListResponseWithBreadcrumbDto {
    private List<WishListResponseDto> wishLists;
    private List<BreadcrumbItem> breadcrumbs;

    public WishListResponseWithBreadcrumbDto(List<WishListResponseDto> wishLists, List<BreadcrumbItem> breadcrumbs) {
        this.wishLists = wishLists;
        this.breadcrumbs = breadcrumbs;
    }

    public List<WishListResponseDto> getWishLists() {
        return wishLists;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setWishLists(List<WishListResponseDto> wishLists) {
        this.wishLists = wishLists;
    }

    public void setBreadcrumbs(List<BreadcrumbItem> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
