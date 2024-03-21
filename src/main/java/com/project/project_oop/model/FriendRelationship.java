package com.project.project_oop.model;

import com.project.project_oop.constant.FriendStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "friend_relationship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRelationship extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long friendId;

    @Enumerated(EnumType.STRING)
    private FriendStatus status = FriendStatus.NORMAL;

    private Date createdAt = new Date();
}
