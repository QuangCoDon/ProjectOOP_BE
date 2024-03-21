package com.project.project_oop.model;

import com.project.project_oop.constant.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "password")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseModel{

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String about;

    private String avatar;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private Date passwordChangedAt;

    private String passwordResetToken;

    private Date passwordResetExpires;

    private Date createdAt = new Date();

    private Date updatedAt;

    private boolean verified = false;

    private String otp;

    private Date optExpiryTime;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Token> tokens = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<FriendRelationship> friendRelationships = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(
            mappedBy = "sender",
            fetch = FetchType.LAZY
    )
    private List<FriendRequest> friendRequestsSend = new ArrayList<>();

    @OneToMany(
            mappedBy = "receiver",
            fetch = FetchType.LAZY
    )
    private List<FriendRequest> friendRequestsReceive = new ArrayList<>();

    @OneToMany(
            mappedBy = "sender",
            fetch = FetchType.LAZY
    )
    private List<Message> messages = new ArrayList<>();

}