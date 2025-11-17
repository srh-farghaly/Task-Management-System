package com.taskmanagement.taskmanager.models;

public interface TaskObserver {
    void update(Task task, String changeType);
}
