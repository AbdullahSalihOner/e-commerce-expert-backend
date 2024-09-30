package com.salih.dto.review;

import com.salih.model.BreadcrumbItem;

import java.util.List;

public class ReviewResponseWithBreadcrumbDto {
    private List<ReviewResponseDto> reviews;
    private List<BreadcrumbItem> breadcrumbs;

    public ReviewResponseWithBreadcrumbDto(List<ReviewResponseDto> reviews, List<BreadcrumbItem> breadcrumbs) {
        this.reviews = reviews;
        this.breadcrumbs = breadcrumbs;
    }

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setReviews(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    public void setBreadcrumbs(List<BreadcrumbItem> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
