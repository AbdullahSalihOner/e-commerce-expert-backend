package com.salih.dto.wishList;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListResponseDto {

    private Long id;

    private String userName;

    private List<String> productNames;
}
