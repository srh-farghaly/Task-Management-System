package com.taskmanagement.taskmanager.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Entity
public class FeatureTask extends Task{
    @Enumerated(EnumType.STRING)
    private RiskLevel risklevel;

    private String TargetVersion;

    private Integer EstimatedHours;

    public FeatureTask(){}

    public FeatureTask(Long id,
                   String title,
                   String description,
                   Priority priority,
                   Status status,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   RiskLevel risklevel,
                   Integer EstimatedHour,
                   String TargetVersion) {
        super(id,title,description,priority,status,createdAt,updatedAt); // parent fields
        this.EstimatedHours = EstimatedHour;
        this.risklevel = risklevel;
        this.TargetVersion = TargetVersion;

    }


    // Getters and setters

    public RiskLevel getRiskLevel() {
        return risklevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.risklevel = riskLevel;
    }

    public String getTargetVersion() {
        return TargetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.TargetVersion = targetVersion;
    }

    public Integer getEstimatedHours() {
        return EstimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {  // ← Change to Integer
        this.EstimatedHours = estimatedHours;
    }



}
