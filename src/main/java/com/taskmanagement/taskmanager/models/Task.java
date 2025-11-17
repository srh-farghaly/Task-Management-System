package com.taskmanagement.taskmanager.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// adding @inheritance annotation for child classes to inherit task as the bug and feature.

@Inheritance
@Entity                           // "This is a database table"
@Table(name = "tasks")            // "Table name is 'tasks'"
public class Task implements TaskSubject {

    @Transient  // ← ADD THIS - tells JPA "don't save this to database"
    private List<TaskObserver> observers = new ArrayList<>();


    @Id                         // "This is the primary key"
    @GeneratedValue(strategy = GenerationType.IDENTITY)             // "Auto-increment the ID"  :: Uses the database’s AUTO_INCREMENT on insert.
    private Long id;

    @Column(nullable = false)     // "This field is required"
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)      // "Store as text, not number"
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private TaskType type;  // ← ADD THIS

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

//    What this does:
//    @ElementCollection - JPA creates separate table for tags
//    @CollectionTable - Names the table "task_tags"
//    Creates one-to-many relationship (one task, many tags)
    @ElementCollection
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Column(length = 2000)
    private String comments;

    private LocalDate dueDate;

    // Constructors
    public Task() {}

    public Task(Long id, String title, String description, Priority priority, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    // ADD getter/setter
    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate=dueDate;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    @PrePersist                           //  @PrePersist runs once right before the first insert:
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = Status.TODO;
        }
        if (priority == null) {
            priority = Priority.MEDIUM;
        }
    }

    @PreUpdate                 //  @PreUpdate runs every update: refreshes the update date
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    @Override
    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String changeType){
        for (TaskObserver observer : observers) {
            observer.update(this,changeType);
        }
    }
}

// Task just forwards it to observers - doesn't need to store it. the change type