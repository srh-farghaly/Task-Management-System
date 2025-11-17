package com.taskmanagement.taskmanager.services;

import com.taskmanagement.taskmanager.models.*;
import org.springframework.stereotype.Service;

@Service
public class TaskFactory {

    public Task createTask(
            TaskType type,
            String title,
            String description,
            Priority priority,
            Status status,
            // Optional type-specific parameters
            BugStatus bugStatus,
            Severity severity,
            String stepsToReproduce,
            RiskLevel riskLevel,
            Integer estimatedHours,
            String targetVersion
    ) {
        if (type == null) {
            throw new IllegalArgumentException("Task type cannot be null");
        }

        switch (type) {
            case BUG:
                BugTask bugTask = new BugTask(
                        null,
                        title,
                        description,
                        priority,
                        status,
                        null,
                        null,
                        severity,              // ← Use parameter instead of null
                        stepsToReproduce,      // ← Use parameter
                        bugStatus
                );
                bugTask.setType(TaskType.BUG);
                return bugTask;

            case FEATURE:
                FeatureTask featureTask = new FeatureTask(
                        null,
                        title,
                        description,
                        priority,
                        status,
                        null,
                        null,
                        riskLevel,             // ← Use parameter
                        estimatedHours,        // ← Use parameter
                        targetVersion          // ← Use parameter
                );
                featureTask.setType(TaskType.FEATURE);
                return featureTask;

            default:
                throw new IllegalArgumentException("Unknown Task type: " + type);
        }
    }
}