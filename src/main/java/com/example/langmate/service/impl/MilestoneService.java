package com.example.langmate.service.impl;

import com.example.langmate.controller.payload.request.MilestoneRequest;
import com.example.langmate.domain.entities.Milestone;
import com.example.langmate.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

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

    public void deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }
}
