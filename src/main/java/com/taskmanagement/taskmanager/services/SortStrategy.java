package com.taskmanagement.taskmanager.services;


import com.taskmanagement.taskmanager.models.Task;
import java.util.ArrayList;
import java.util.List;

public interface SortStrategy {
    List <Task> sort(List<Task>tasks);
}
