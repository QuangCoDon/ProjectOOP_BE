package com.project.project_oop.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class LoginRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
