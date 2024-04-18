package com.project.project_oop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    @JsonProperty("is_error")
    private Long isError;

    @JsonProperty("error_message")
    private String errorMessage;
}
