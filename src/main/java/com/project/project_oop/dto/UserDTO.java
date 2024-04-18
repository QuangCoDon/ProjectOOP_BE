package com.project.project_oop.dto;

import com.project.project_oop.model.constant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String about;

    private String avatar;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String country;

    private String city;

    private String address;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}
