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

    @Size(min = 3, max = 10, message = "Invalid first name!(3-10 characters)")
    private String firstName;
    @Size(min = 3, max = 10, message = "Invalid first name!(3-10 characters)")
    private String lastName;

    @Email
    @NotBlank(message = "Email should not be empty")
    private String email;

    @Size(min = 8, max = 16, message = "Invalid password!(8-16 characters)")
    private String password;

    private String repeatPassword;
}
