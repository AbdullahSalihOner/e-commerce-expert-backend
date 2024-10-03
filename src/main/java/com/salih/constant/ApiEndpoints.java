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
    public static final String PRODUCT_GET_BY_USER_ID = "/user/{userId}";

    // Order Endpoints
    public static final String ORDER_BASE = "/api/orders";
    public static final String ORDER_GET_BY_ID = "/{id}";
    public static final String ORDER_PLACE = "/place";
    public static final String ORDER_GET_ALL = "/all";
    public static final String ORDER_CANCEL = "/cancel/{id}";

    // User Endpoints
    public static final String USER_BASE = "/api/users";
    public static final String USER_GET_BY_ID = "/{id}";
    public static final String USER_CREATE = "/create";
    public static final String USER_UPDATE = "/update/{id}";
    public static final String USER_DELETE = "/delete/{id}";
    public static final String USER_GET_ALL = "/all";
    public static final String USER_LOGIN = "/login";

    // WishList Endpoints
    public static final String WISHLIST_BASE = "/api/wishlists";
    public static final String WISHLIST_GET_ALL = "/all";
    public static final String WISHLIST_ADD = "/add";
    public static final String WISHLIST_GET_BY_USER_ID = "/user/{userId}";
    public static final String WISHLIST_DELETE = "/delete/{id}";

    // Review Endpoints
    public static final String REVIEW_BASE = "/api/reviews";
    public static final String REVIEW_ADD = "/add";
    public static final String REVIEW_GET_ALL = "/all";
    public static final String REVIEW_GET_BY_PRODUCT_ID = "/product/{productId}";
    public static final String REVIEW_DELETE = "/delete/{id}";
    public static final String REVIEW_UPDATE = "/update/{id}";

    // Category Endpoints
    public static final String CATEGORY_BASE = "/api/categories";
    public static final String CATEGORY_GET_ALL = "/all";
    public static final String CATEGORY_GET_BY_ID = "/{id}";
    public static final String CATEGORY_CREATE = "/create";
    public static final String CATEGORY_UPDATE = "/update/{id}";
    public static final String CATEGORY_DELETE = "/delete/{id}";

    // Shipment Endpoints
    public static final String SHIPMENT_BASE = "/api/shipments";
    public static final String SHIPMENT_GET_ALL = "/all";
    public static final String SHIPMENT_GET_BY_ID = "/{id}";
    public static final String SHIPMENT_CREATE = "/create";
    public static final String SHIPMENT_UPDATE = "/update/{id}";
    public static final String SHIPMENT_UPDATE_STATUS = "/update-status/{id}";
    public static final String SHIPMENT_DELETE = "/delete/{id}";
    public static final String ORDER_UPDATE_STATUS = "/update-status/{id}";
    public static final String USER_LOGOUT = "/logout";
    public static final String USER_SIGNUP = "/signup";



    // Other Endpoints (if applicable)
    // Add more as needed...
}
