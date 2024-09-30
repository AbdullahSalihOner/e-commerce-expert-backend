package com.salih.dto.product;

import com.salih.dto.product.ProductResponseDto;
import com.salih.model.BreadcrumbItem;

import java.util.List;

public class ProductResponseWithBreadcrumbDto {
    private List<ProductResponseDto> products;
    private List<BreadcrumbItem> breadcrumbs;

    public ProductResponseWithBreadcrumbDto(List<ProductResponseDto> products, List<BreadcrumbItem> breadcrumbs) {
        this.products = products;
        this.breadcrumbs = breadcrumbs;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public void setBreadcrumbs(List<BreadcrumbItem> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
