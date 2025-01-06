package com.example.langmate.repository;

import com.example.langmate.domain.entities.MilestoneUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneUserRepository extends JpaRepository<MilestoneUser, Long> {
    boolean existsByUserIdAndMilestoneId(Long userId, Long milestoneId);

}
