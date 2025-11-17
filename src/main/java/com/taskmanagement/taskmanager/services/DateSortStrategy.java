package com.taskmanagement.taskmanager.services;

import com.taskmanagement.taskmanager.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class DateSortStrategy implements SortStrategy{
    @Override
    public List<Task> sort(List<Task> tasks){
        List<Task> sortedTasks = new ArrayList<>(tasks);
        // Descending: newest tasks first
        sortedTasks.sort(Comparator.comparing(Task::getCreatedAt).reversed());
        return sortedTasks;
    }
}
