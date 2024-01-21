package com.leirbaggr.paymentsystem.dto;

import com.leirbaggr.paymentsystem.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull(message = "O nome não pode ser nulo")
        @NotBlank(message = "Campo não pode ser vazio")
        String name,
        @NotNull(message = "O email não pode ser nulo")
        @NotBlank(message = "Campo não pode ser vazio")
        @Email
        String email,
        @NotNull(message = "A senha não pode ser nula")
        @NotBlank(message = "Campo não pode ser vazio")
        @Size(min = 8, message = "Senha com no minimo 8 caracteres")
        String password) {
    public User toModel() {
        return new User(name, email, password);
    }
}
