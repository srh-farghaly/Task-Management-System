package com.taskmanagement.taskmanager.repositories;
import com.taskmanagement.taskmanager.models.Task;
import com.taskmanagement.taskmanager.models.Status;
import com.taskmanagement.taskmanager.models.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring automatically implements these methods!
// You just declare them, Spring creates the SQL
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByTitleContaining(String keyword);
// Spring gives you for FREE:
// - save(task)
// - findAll()
// - findById(id)
// - deleteById(id)
// - count()
}
