package com.example.langmate.domain.entities;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone; // legatura cu milestone
}
