package com.example.langmate.domain.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Milestone {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private int targetValue; // ex: 5 finished lessons
    private String targetType; // ex: "lessons_completed"

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reward> rewards;

}
