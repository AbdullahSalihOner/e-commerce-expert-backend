package com.salih.dto.user;

import com.salih.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;


    private List<User.UserRole> role; // BUYER, SELLER, ADMIN, CARGO_COURIER
}
