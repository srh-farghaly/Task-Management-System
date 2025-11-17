package com.taskmanagement.taskmanager.services;

import com.taskmanagement.taskmanager.models.Status;
import com.taskmanagement.taskmanager.models.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatusSortStrategy implements SortStrategy {

    @Override
    public List<Task> sort(List<Task> tasks) {
        // make a copy so we don't modify the original list
        List<Task> sortedTasks = new ArrayList<>(tasks);

        sortedTasks.sort(Comparator.comparingInt(task -> getStatusOrder(task.getStatus())));

        return sortedTasks;
    }

    private int getStatusOrder(Status status) {
        if (status == Status.TODO) {
            return 0;
        } else if (status == Status.IN_PROGRESS) {
            return 1;
        } else if (status == Status.DONE) {
            return 2;
        } else {
            return 3; // any other/unexpected status goes last
        }
    }
}
