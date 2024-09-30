package com.salih.dto.order;

import com.salih.model.BreadcrumbItem;

import java.util.List;

public class OrderResponseWithBreadcrumbDto {
    private List<OrderResponseDto> orders;
    private List<BreadcrumbItem> breadcrumbs;

    public OrderResponseWithBreadcrumbDto(List<OrderResponseDto> orders, List<BreadcrumbItem> breadcrumbs) {
        this.orders = orders;
        this.breadcrumbs = breadcrumbs;
    }

    public List<OrderResponseDto> getOrders() {
        return orders;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setOrders(List<OrderResponseDto> orders) {
        this.orders = orders;
    }

    public void setBreadcrumbs(List<BreadcrumbItem> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
