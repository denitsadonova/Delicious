package org.example.models.binding;

import jakarta.validation.constraints.*;

public class UserRegisterBindingModel {
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters!")
    @NotBlank(message = "{errorRequiredField}")
    private String username;
    @Size(min = 6,  message = "Password must be at least 6 characters!")
    @NotBlank(message = "{errorRequiredField}")
    private String password;
    @Size(min = 6,  message = "Password must be at least 6 characters!")
    @NotBlank(message = "{errorRequiredField}")
    private String confirmPassword;
    @Email(message = "Enter valid Email!")
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
