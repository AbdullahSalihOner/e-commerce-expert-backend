package com.salih.dto.wishList;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListRequestDto {
    private Long userId;  // User ID'sini kullanıyoruz
    private List<Long> productIds;
}
