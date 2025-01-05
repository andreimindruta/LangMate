package com.example.langmate.repository;

import com.example.langmate.domain.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
}
