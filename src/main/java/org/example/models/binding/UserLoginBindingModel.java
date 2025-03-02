package org.example.models.binding;

import jakarta.validation.constraints.NotBlank;

public class UserLoginBindingModel {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public UserLoginBindingModel() {
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
}
