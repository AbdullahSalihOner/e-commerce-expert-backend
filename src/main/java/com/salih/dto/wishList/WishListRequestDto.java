package com.salih.dto.wishList;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private List<Long> productIds;
}
