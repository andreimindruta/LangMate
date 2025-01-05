package com.example.langmate.controller;

import com.example.langmate.controller.payload.request.MilestoneRequest;
import com.example.langmate.controller.payload.request.PostRewardRequest;
import com.example.langmate.controller.payload.response.GetMilestoneResponse;
import com.example.langmate.controller.payload.response.GetRewardResponse;
import com.example.langmate.domain.entities.Milestone;
import com.example.langmate.domain.entities.Reward;
import com.example.langmate.service.impl.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/langmate/milestones")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping
    public ResponseEntity<List<GetMilestoneResponse>> getAllMilestones() {
        List<Milestone> milestones = milestoneService.getAllMilestones();
        List<GetMilestoneResponse> responses = milestones.stream()
                .map(milestone -> new GetMilestoneResponse(
                        milestone.getId(),
                        milestone.getName(),
                        milestone.getDescription(),
                        milestone.getTargetValue(),
                        milestone.getTargetType()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMilestoneResponse> getMilestoneById(@PathVariable Long id) {
        Milestone milestone = milestoneService.getMilestoneById(id);
        GetMilestoneResponse response = new GetMilestoneResponse(
                milestone.getId(),
                milestone.getName(),
                milestone.getDescription(),
                milestone.getTargetValue(),
                milestone.getTargetType()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<GetMilestoneResponse> createMilestone(@RequestBody MilestoneRequest request) {
        Milestone milestone = milestoneService.createMilestone(request);
        GetMilestoneResponse response = new GetMilestoneResponse(
                milestone.getId(),
                milestone.getName(),
                milestone.getDescription(),
                milestone.getTargetValue(),
                milestone.getTargetType()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/rewards")
    public ResponseEntity<GetRewardResponse> addRewardToMilestone(@PathVariable Long id, @RequestBody PostRewardRequest request) {
        Reward reward = milestoneService.addRewardToMilestone(id, request);
        GetRewardResponse response = new GetRewardResponse(
                reward.getId(),
                reward.getName(),
                reward.getDescription()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.deleteMilestone(id);
        return ResponseEntity.noContent().build();
    }
}
