package com.taskmanagement.taskmanager.dto;

import java.time.LocalDate;

public class DueDateRequest {
    private LocalDate dueDate;

    public DueDateRequest() {}

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}