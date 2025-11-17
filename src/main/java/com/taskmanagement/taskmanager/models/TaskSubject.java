package com.taskmanagement.taskmanager.models;

public interface TaskSubject {

    void addObserver(TaskObserver observer);
    void removeObserver(TaskObserver observer);
    void notifyObservers(String changeType);
}
