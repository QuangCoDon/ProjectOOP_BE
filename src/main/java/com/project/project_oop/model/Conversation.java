package com.project.project_oop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "conversation"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversation extends BaseModel {

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "conversation")
    private List<Participant> participants = new ArrayList<>();
}
