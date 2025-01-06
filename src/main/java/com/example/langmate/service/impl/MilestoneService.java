package com.example.langmate.service.impl;

import com.example.langmate.controller.payload.request.MilestoneRequest;
import com.example.langmate.domain.entities.Milestone;
import com.example.langmate.domain.entities.MilestoneTargetType;
import com.example.langmate.domain.entities.MilestoneUser;
import com.example.langmate.domain.entities.User;
import com.example.langmate.repository.MilestoneRepository;
import com.example.langmate.repository.MilestoneUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MilestoneService {

    private static final Logger logger = LoggerFactory.getLogger(MilestoneService.class);

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private MilestoneUserRepository milestoneUserRepository;

    @Autowired
    @Lazy
    private ResultService resultService;

    @Autowired
    @Lazy
    private TestService testService;

    public List<Milestone> getAllMilestones() {
        logger.info("Fetching all milestones from the database");
        return milestoneRepository.findAll();
    }

    public Milestone getMilestoneById(Long id) {
        logger.info("Fetching milestone with ID: {}", id);
        return milestoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));
    }

    public Milestone createMilestone(MilestoneRequest request) {
        logger.info("Creating milestone with name: {}, targetValue: {}, targetType: {}",
                request.name(), request.targetValue(), request.targetType());
        Milestone milestone = new Milestone();
        milestone.setName(request.name());
        milestone.setDescription(request.description());
        milestone.setTargetValue(request.targetValue());
        try {
            milestone.setTargetType(MilestoneTargetType.valueOf(request.targetType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid target type: {}", request.targetType());
            throw new RuntimeException("Invalid target type: " + request.targetType());
        }
        return milestoneRepository.save(milestone);
    }

    public void deleteMilestone(Long id) {
        logger.info("Deleting milestone with ID: {}", id);
        milestoneRepository.deleteById(id);
    }

    /**
     * Verifică progresul unui utilizator și atribuie milestone-uri.
     */
    public void checkAndAssignMilestones(Long userId, Long languageId) {
        logger.info("Checking milestones for userId: {}, languageId: {}", userId, languageId);
        List<Milestone> milestones = milestoneRepository.findAll();

        for (Milestone milestone : milestones) {
            logger.info("Processing milestone: {}, targetType: {}, targetValue: {}",
                    milestone.getName(), milestone.getTargetType(), milestone.getTargetValue());
            boolean achieved = false;

            switch (milestone.getTargetType()) {
                case GRADE:
                    Double averageGrade = resultService.getAverageGradeForUserAndLanguage(userId, languageId);
                    logger.info("Average grade for userId: {}, languageId: {} is {}", userId, languageId, averageGrade);
                    if (averageGrade != null && averageGrade >= milestone.getTargetValue()) {
                        achieved = true;
                    }
                    break;

                case TESTS:
                    int completedTests = resultService.getTestsCountForUserAndLanguage(userId, languageId);
                    logger.info("Completed tests for userId: {}, languageId: {} is {}", userId, languageId, completedTests);
                    if (completedTests >= milestone.getTargetValue()) {
                        achieved = true;
                    }
                    break;

                default:
                    logger.error("Invalid targetType: {}", milestone.getTargetType());
                    throw new IllegalArgumentException("Invalid targetType: " + milestone.getTargetType());
            }

            if (achieved) {
                logger.info("Milestone achieved: {} for userId: {}", milestone.getName(), userId);
                assignMilestoneToUser(userId, milestone);
            } else {
                logger.info("Milestone not achieved: {} for userId: {}", milestone.getName(), userId);
            }
        }
    }

    /**
     * Atribuie milestone-ul unui utilizator.
     */
    private void assignMilestoneToUser(Long userId, Milestone milestone) {
        logger.info("Assigning milestone: {} to userId: {}", milestone.getName(), userId);
        if (!milestoneUserRepository.existsByUserIdAndMilestoneId(userId, milestone.getId())) {
            MilestoneUser milestoneUser = new MilestoneUser();
            milestoneUser.setUser(new User(userId));
            milestoneUser.setMilestone(milestone);
            milestoneUserRepository.save(milestoneUser);
            logger.info("Milestone assigned successfully: {} to userId: {}", milestone.getName(), userId);
        } else {
            logger.info("Milestone: {} already assigned to userId: {}", milestone.getName(), userId);
        }
    }
}
