package com.project.project_oop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    private boolean revoked;

    private boolean expired;

}
