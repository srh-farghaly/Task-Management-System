package com.taskmanagement.taskmanager.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BugTask extends Task{

// unique properties for the bug;
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated (EnumType.STRING)
    private BugStatus Bugstatus;

    // specific for bugs only;

    private String stepsToReproduce;
    public BugTask() {}

    public BugTask(Long id,
                   String title,
                   String description,
                   Priority priority,
                   Status status,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   Severity severity,
                   String stepsToReproduce,
                   BugStatus bugstatus) {

        super(id,title,description,priority,status,createdAt,updatedAt); // parent fields
        this.severity = severity;                          // child field
        this.stepsToReproduce = stepsToReproduce;
        this.Bugstatus=bugstatus;// child field
    }
// getter setter for steps to reproduce
    public void setStepsToReproduce(String stepsToReproduce){
        this.stepsToReproduce=stepsToReproduce;
    }

    public String getStepsToReproduce(){
        return stepsToReproduce;
    }

    // getter and setter for severity
    public void setSeverity(Severity s)
    {
        this.severity=s;
    }

    public Severity getSeverity(){
        return this.severity;
    }

    // getter and setter for bugstatus
    public void setBugStatus(BugStatus bug)
    {
        this.Bugstatus = bug;
    }


    public BugStatus getBugstatus()
    {
        return this.Bugstatus;
    }

    @PrePersist
    protected void setDefaults() {
        super.onCreate();  // Call parent's @PrePersist
        System.out.println("Severity: " + severity);  // ← DEBUG
        if (severity == Severity.CRITICAL) {
            System.out.println("Setting priority to HIGH");  // ← DEBUG

            this.setPriority(Priority.HIGH);
        }
    }





}
