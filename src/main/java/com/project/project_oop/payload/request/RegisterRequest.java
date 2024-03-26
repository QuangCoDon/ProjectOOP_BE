package com.project.project_oop.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class RegisterRequest {

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String about;

    private String avatar;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
