package com.taskmanagement.taskmanager.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskmanagement.taskmanager.models.*;

public class TaskCreateRequest {

    // Common fields (all tasks)
    @JsonProperty("type")
    private TaskType type;
    @JsonProperty("assigneeId")
    private Long assigneeId;  // We pass user ID, not entire User object

    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;

    @JsonProperty("priority")
    private Priority priority;

    // Bug-specific (optional)
    @JsonProperty("severity")
    private Severity severity;
    @JsonProperty("stepsToReproduce")
    private String stepsToReproduce;

    // Feature-specific (optional)
    @JsonProperty("riskLevel")
    private RiskLevel riskLevel;

    @JsonProperty("estimatedHours")
    private Integer estimatedHours;

    @JsonProperty("targetVersion")
    private String targetVersion;

    @JsonProperty("bugStatus")  // ← ADD THIS
    private BugStatus bugStatus;  // ← ADD THIS


    public TaskCreateRequest() {
        // Default constructor for Jackson
    }

    // TODO: Add getters and setters for ALL fields

    public TaskType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setStepsToReproduce(String stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public BugStatus getBugStatus() {
        return bugStatus;
    }
    public void setBugStatus(BugStatus bugStatus) {
        this.bugStatus = bugStatus;
    }
}