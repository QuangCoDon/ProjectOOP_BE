package com.project.project_oop.payload.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class RegisterRequest {

    @NotNull
    @JsonProperty("username")
    private String username;

    @NotNull
    @JsonProperty("firstname")
    private String firstName;

    @NotNull
    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("about")
    private String about;

    @JsonProperty("avatar")
    private String avatar;

    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("password")
    private String password;

}
