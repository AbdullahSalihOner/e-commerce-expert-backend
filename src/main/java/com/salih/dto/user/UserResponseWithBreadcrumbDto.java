package com.salih.dto.user;

import com.salih.model.BreadcrumbItem;

import java.util.List;

public class UserResponseWithBreadcrumbDto {
    private List<UserResponseDto> users;
    private List<BreadcrumbItem> breadcrumbs;

    public UserResponseWithBreadcrumbDto(List<UserResponseDto> users, List<BreadcrumbItem> breadcrumbs) {
        this.users = users;
        this.breadcrumbs = breadcrumbs;
    }

    public List<UserResponseDto> getUsers() {
        return users;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setUsers(List<UserResponseDto> users) {
        this.users = users;
    }

    public void setBreadcrumbs(List<BreadcrumbItem> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
