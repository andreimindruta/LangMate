package com.example.langmate.service.impl;

import com.example.langmate.controller.payload.request.MilestoneRequest;
import com.example.langmate.controller.payload.request.PostRewardRequest;
import com.example.langmate.domain.entities.Milestone;
import com.example.langmate.domain.entities.Reward;
import com.example.langmate.repository.MilestoneRepository;
import com.example.langmate.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private RewardRepository rewardRepository;

    public List<Milestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }

    public Milestone getMilestoneById(Long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));
    }

    public Milestone createMilestone(MilestoneRequest request) {
        Milestone milestone = new Milestone();
        milestone.setName(request.name());
        milestone.setDescription(request.description());
        milestone.setTargetValue(request.targetValue());
        milestone.setTargetType(request.targetType());
        return milestoneRepository.save(milestone);
    }

    public Reward addRewardToMilestone(Long milestoneId, PostRewardRequest postRewardRequest) {
        Milestone milestone = getMilestoneById(milestoneId);
        Reward reward = new Reward();
        reward.setName(postRewardRequest.name());
        reward.setDescription(postRewardRequest.description());
        reward.setMilestone(milestone);
        return rewardRepository.save(reward);
    }

    public void deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }
}
