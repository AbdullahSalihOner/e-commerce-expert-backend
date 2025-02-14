package com.salih.dto.user;

import com.salih.model.User;
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
public class UserResponseDto  implements Serializable {

    private Long id;

    private String name;

    private String email;

    private List<User.UserRole> role;
}
