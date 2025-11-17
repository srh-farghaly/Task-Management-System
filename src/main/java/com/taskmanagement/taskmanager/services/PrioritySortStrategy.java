package com.taskmanagement.taskmanager.services;

import com.taskmanagement.taskmanager.models.Priority;
import com.taskmanagement.taskmanager.models.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PrioritySortStrategy implements SortStrategy {

    @Override
    public List<Task> sort(List<Task> tasks) {
        // Create a copy to avoid modifying original list
        List<Task> sortedTasks = new ArrayList<>(tasks);

        // Sort using Comparator
        sortedTasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return getPriorityValue(t1.getPriority()) - getPriorityValue(t2.getPriority());
            }
        });

        return sortedTasks;
    }

    // Helper method: Convert priority to number for comparison
    private int getPriorityValue(Priority priority) {
        switch (priority) {
            case HIGH: return 1;
            case MEDIUM: return 2;
            case LOW: return 3;
            default: return 4;
        }
    }
}