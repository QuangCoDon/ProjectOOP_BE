package com.project.project_oop.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenDTO {

    private Long id;

    private String token;

    private boolean revoked;

    private boolean expired;

}
