package com.project.project_oop.payload.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.project_oop.payload.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse extends BaseResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

}
