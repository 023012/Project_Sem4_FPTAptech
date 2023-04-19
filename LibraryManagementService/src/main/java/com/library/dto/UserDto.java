package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @Size(min = 3, max = 40, message = "Invalid last name!(3-40 characters)")
    private String fullName;

    @Email
    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotBlank(message = "Username should not be empty")
    private String username;

    @Size(min = 6, max = 20, message = "Invalid password!(5-15 characters)")
    private String password;

    private String repeatPassword;
}
