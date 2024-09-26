package com.salih.constant;

public class ApiEndpoints {

    // Product Endpoints
    public static final String PRODUCT_BASE = "/api/products";
    public static final String PRODUCT_GET_BY_ID = "/{id}";
    public static final String PRODUCT_ADD = "/add";
    public static final String PRODUCT_UPDATE = "/update/{id}";
    public static final String PRODUCT_DELETE = "/delete/{id}";
    public static final String PRODUCT_GET_ALL = "/all";
    public static final String PRODUCT_GET_BY_CATEGORY = "/category/{categoryId}";

    // Order Endpoints
    public static final String ORDER_BASE = "/api/orders";
    public static final String ORDER_GET_BY_ID = "/{id}";
    public static final String ORDER_PLACE = "/place";
    public static final String ORDER_GET_ALL = "/all";

    // User Endpoints
    public static final String USER_BASE = "/api/users";
    public static final String USER_GET_BY_ID = "/{id}";
    public static final String USER_CREATE = "/create";
    public static final String USER_UPDATE = "/update/{id}";
    public static final String USER_DELETE = "/delete/{id}";
    public static final String USER_GET_ALL = "/all";

    // WishList Endpoints
    public static final String WISHLIST_BASE = "/api/wishlists";
    public static final String WISHLIST_GET_ALL = "/all";
    public static final String WISHLIST_ADD = "/add";
    public static final String WISHLIST_GET_BY_USER_ID = "/user/{userId}";

    // Review Endpoints
    public static final String REVIEW_BASE = "/api/reviews";
    public static final String REVIEW_ADD = "/add";
    public static final String REVIEW_GET_ALL = "/all";
    public static final String REVIEW_GET_BY_PRODUCT_ID = "/product/{productId}";
    public static final String REVIEW_DELETE = "/delete/{id}";

    // Other Endpoints (if applicable)
    // Add more as needed...
}
