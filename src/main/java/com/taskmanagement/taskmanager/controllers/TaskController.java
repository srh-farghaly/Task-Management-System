package com.taskmanagement.taskmanager.controllers;

import com.taskmanagement.taskmanager.dto.CommentRequest;
import com.taskmanagement.taskmanager.models.Task;
import com.taskmanagement.taskmanager.models.Status;
import com.taskmanagement.taskmanager.models.Priority;
import com.taskmanagement.taskmanager.models.BugStatus;
import com.taskmanagement.taskmanager.models.Severity;
import com.taskmanagement.taskmanager.models.RiskLevel;
import com.taskmanagement.taskmanager.models.BugTask;
import com.taskmanagement.taskmanager.models.FeatureTask;
import com.taskmanagement.taskmanager.dto.TaskCreateRequest;


import com.taskmanagement.taskmanager.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // GET /api/tasks - Get all tasks
    @GetMapping
    public List<Task> getAllTasks(@RequestParam(required = false) String sortBy) {

        SortStrategy strategy = null;

        if ("priority".equals(sortBy)) {
            strategy = new PrioritySortStrategy();
        } else if ("date".equals(sortBy)) {
            strategy = new DateSortStrategy();
        } else if ("status".equals(sortBy)) {
            strategy = new StatusSortStrategy();
        }

        return taskService.getAllTasks(strategy);
    }

    // GET /api/tasks/1 - Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/tasks/status/TODO - Get tasks by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Status status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    // GET /api/tasks/priority/HIGH - Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    // GET /api/tasks/search?keyword=bug - Search tasks
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String keyword) {
        List<Task> tasks = taskService.searchTasks(keyword);
        return ResponseEntity.ok(tasks);
    }

    // POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateRequest request) {

        Task createdTask = taskService.createTaskFactory(
                request.getType(),
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                Status.TODO,
                request.getBugStatus(),  // bugStatus
                request.getSeverity(),
                request.getStepsToReproduce(),
                request.getRiskLevel(),
                request.getEstimatedHours(),
                request.getTargetVersion(),
                request.getAssigneeId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // PUT /api/tasks/1 - Update task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task taskDetails
    ) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tasks/1 - Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/tasks/count - Count all tasks
    @GetMapping("/count")
    public ResponseEntity<Long> countTasks() {
        long count = taskService.countTasks();
        return ResponseEntity.ok(count);
    }

    // POST /api/tasks/1/tags - Add tags to task
    @PostMapping("/{id}/tags")
    public ResponseEntity<Task> addTags(
            @PathVariable Long id,
            @RequestBody List<String> tags) {
        try {
            Task updatedTask = taskService.addTags(id, tags);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tasks/1/tags/{tag} - Remove specific tag
    @DeleteMapping("/{id}/tags/{tag}")
    public ResponseEntity<Task> removeTag(
            @PathVariable Long id,
            @PathVariable String tag) {
        try {
            Task updatedTask = taskService.removeTag(id, tag);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/tasks/tags/{tag} - Get all tasks with specific tag
    @GetMapping("/tags/{tag}")
    public ResponseEntity<List<Task>> getTasksByTag(@PathVariable String tag) {
        List<Task> tasks = taskService.getTasksByTag(tag);
        return ResponseEntity.ok(tasks);
    }



    // Set due date - no DTO
    @PostMapping("/{id}/duedate")
    public ResponseEntity<Task> setDueDate(
            @PathVariable Long id,
            @RequestBody LocalDate dueDate) {
        try {
            Task updatedTask = taskService.setDueDate(id, dueDate);  // ← Use dueDate parameter!
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tasks/1/duedate - Remove due date
    @DeleteMapping("/{id}/duedate")
    public ResponseEntity<Task> removeDueDate(@PathVariable Long id) {
        try {
            Task updatedTask = taskService.removeDueDate(id);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // POST /api/tasks/1/comments - Add/Update comments
    @PostMapping("/{id}/comments")
    public ResponseEntity<Task> setComments(
            @PathVariable Long id,
            @RequestBody CommentRequest request) {  // ← Use DTO
        try {
            Task updatedTask = taskService.setComments(id, request.getComment());
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tasks/1/comments - Remove comments
    @DeleteMapping("/{id}/comments")
    public ResponseEntity<Task> removeComments(@PathVariable Long id) {
        try {
            Task updatedTask = taskService.removeComments(id);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}