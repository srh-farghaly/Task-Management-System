package com.taskmanagement.taskmanager.services;
import com.taskmanagement.taskmanager.models.*;
import com.taskmanagement.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanagement.taskmanager.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // This tells Spring: "This is a business logic class"
public class TaskService {
    // Dependency Injection: Spring automatically provides TaskRepository

    @Autowired
    private TaskFactory taskFactory;  // ← ADD THIS

    @Autowired
    private UserRepository userRepository;  // ← ADD THIS
    @Autowired
    private TaskRepository taskRepository;
    // CREATE - Add new task
//    public Task createTask(Task task) {
//        return taskRepository.save(task);
//
//    }

    // create Method with factory design pattern
    public Task createTaskFactory( TaskType type,
                                   String title,
                                   String description,
                                   Priority priority,
                                   Status status,BugStatus bugStatus,
                                   Severity severity,
                                   String stepsToReproduce,
                                   RiskLevel riskLevel,
                                   Integer estimatedHours,
                                   String targetVersion,
                                   Long assigneeId){

        Task task = taskFactory.createTask(type, title, description, priority, Status.TODO,bugStatus,severity,stepsToReproduce,riskLevel,estimatedHours,targetVersion);
        // Set assignee if provided
        if (assigneeId != null) {
            User assignee = userRepository.findById(assigneeId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + assigneeId));
            task.setAssignee(assignee);
            task.addObserver(assignee);
        }
        return taskRepository.save(task);

    }
    // READ - Get all tasks
    public List<Task> getAllTasks(SortStrategy strategy) {
        List<Task> tasks = taskRepository.findAll();
        if(strategy!=null){
            return strategy.sort(tasks);
        }
        return tasks;
    }
    // READ - Get one task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    // READ - Get tasks by status
    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }
    // READ - Get tasks by priority
    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }
    // READ - Search tasks by title
    public List<Task> searchTasks(String keyword) {
        return taskRepository.findByTitleContaining(keyword);
    }
    // UPDATE - Modify existing task
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow();

        // Re-register assignee as observer (observers are @Transient, not loaded from DB)
        if (task.getAssignee() != null) {
            task.addObserver(task.getAssignee());
        }

        // Clone/snapshot the old task
        Task oldTaskSnapshot = createSnapshot(task);

        updateTaskFields(task, taskDetails);
        Task savedTask = taskRepository.save(task);

        // Pass both old and new
        notifyIfChanged(savedTask, oldTaskSnapshot);

        return savedTask;
    }
    // DELETE - Remove task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    // COUNT
    public long countTasks() {
        return taskRepository.count();
    }


    // function to detect changes for design pattern
    private void notifyIfChanged(Task newTask, Task oldTask) {
        // Check whatever you want
        if (!oldTask.getStatus().equals(newTask.getStatus())) {
            newTask.notifyObservers("STATUS_CHANGED");
        }

        if (!oldTask.getPriority().equals(newTask.getPriority())) {
            newTask.notifyObservers("PRIORITY_CHANGED");
        }

        // Easy to add more later without changing method signature!
    }

    private void updateTaskFields(Task task, Task taskDetails) {
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
    }

    private Task createSnapshot(Task task) {
        Task snapshot = new Task();

        // Copy all fields you might need to compare
        snapshot.setStatus(task.getStatus());
        snapshot.setPriority(task.getPriority());
        snapshot.setAssignee(task.getAssignee());
        snapshot.setTitle(task.getTitle());
        snapshot.setDescription(task.getDescription());

        return snapshot;
    }

    // Add tags to task
    public Task addTags(Long taskId, List<String> tags) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Add new tags (avoid duplicates)
        for (String tag : tags) {
            if (!task.getTags().contains(tag)) {
                task.getTags().add(tag);
            }
        }

        return taskRepository.save(task);
    }

    // Remove tag from task
    public Task removeTag(Long taskId, String tag) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.getTags().remove(tag);

        return taskRepository.save(task);
    }

    // Get tasks by tag
    public List<Task> getTasksByTag(String tag) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getTags().contains(tag))
                .collect(Collectors.toList());
    }
    public Task setDueDate(Long taskId, LocalDate dueDate){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not Found"));
        task.setDueDate(dueDate);
        return taskRepository.save(task);
    }

    // Remove due date
    public Task removeDueDate(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setDueDate(null);

        return taskRepository.save(task);
    }

    // Get tasks due before a date
    public List<Task> getTasksDueBefore(LocalDate date) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(date))
                .collect(Collectors.toList());
    }


    // Add/Update comments
    public Task setComments(Long taskId, String comments) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setComments(comments);

        return taskRepository.save(task);
    }

    // Remove comments
    public Task removeComments(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setComments(null);

        return taskRepository.save(task);
    }
}