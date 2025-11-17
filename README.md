# Task Management System

Spring Boot REST API implementing multiple design patterns for task management.

## Tech Stack

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database
- Maven

## Design Patterns Implemented

### 1. Factory Pattern
Creates different task types (Bug, Feature) with type-specific fields.

**Location:** `services/TaskFactory.java`

### 2. Observer Pattern
Notifies users when their assigned tasks change status or priority.

**Components:**
- `models/TaskObserver.java` - Observer interface
- `models/TaskSubject.java` - Subject interface
- `models/User.java` - Implements Observer
- `models/Task.java` - Implements Subject

### 3. Strategy Pattern
Sorts tasks using interchangeable algorithms.

**Strategies:**
- `PrioritySortStrategy` - Sort by HIGH → MEDIUM → LOW
- `DateSortStrategy` - Sort by creation date
- `StatusSortStrategy` - Sort by TODO → IN_PROGRESS → DONE

**Location:** `services/SortStrategy.java`

### 4. Decorator Pattern (Practical)
Add optional features to tasks dynamically.

**Features:**
- Tags (categorization)
- Due dates
- Comments/notes

## Core Features

- Create Bug and Feature tasks
- Assign tasks to users
- Update task status/priority (triggers notifications)
- Sort tasks by priority, date, or status
- Add tags, due dates, and comments to any task
- Search tasks by keyword
- Filter by status or priority

## API Endpoints

### Tasks
```
POST   /api/tasks                    - Create task
GET    /api/tasks                    - Get all tasks (optional: ?sortBy=priority|date|status)
GET    /api/tasks/{id}               - Get task by ID
PUT    /api/tasks/{id}               - Update task
DELETE /api/tasks/{id}               - Delete task
GET    /api/tasks/status/{status}    - Filter by status
GET    /api/tasks/priority/{priority} - Filter by priority
GET    /api/tasks/search?keyword=x   - Search tasks
```

### Users
```
POST   /api/users        - Create user
GET    /api/users        - Get all users
GET    /api/users/{id}   - Get user by ID
```

### Decorators (Optional Features)
```
POST   /api/tasks/{id}/tags          - Add tags
DELETE /api/tasks/{id}/tags/{tag}    - Remove tag
GET    /api/tasks/tags/{tag}         - Get tasks by tag
POST   /api/tasks/{id}/duedate       - Set due date
DELETE /api/tasks/{id}/duedate       - Remove due date
POST   /api/tasks/{id}/comments      - Add/update comment
DELETE /api/tasks/{id}/comments      - Remove comment
```

## Database Schema

### Tasks Table
- Inheritance: SINGLE_TABLE with discriminator column (DTYPE)
- Bug tasks: severity, bugStatus, stepsToReproduce
- Feature tasks: riskLevel, estimatedHours, targetVersion
- Common: title, description, priority, status, assignee

### Task Tags Table
- Separate table for tags (one-to-many)

### Users Table
- User information and relationships

## Running the Application
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

Application runs on: `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: (empty)

## Example Requests

### Create Bug Task
```json
POST /api/tasks
{
  "type": "BUG",
  "title": "Login fails",
  "priority": "HIGH",
  "severity": "CRITICAL",
  "bugStatus": "OPEN",
  "stepsToReproduce": "1. Go to login\n2. Enter credentials\n3. Click submit",
  "assigneeId": 1
}
```

### Create Feature Task
```json
POST /api/tasks
{
  "type": "FEATURE",
  "title": "Add dashboard",
  "priority": "MEDIUM",
  "riskLevel": "LOW",
  "estimatedHours": 40,
  "targetVersion": "2.0.0"
}
```

### Sort Tasks
```
GET /api/tasks?sortBy=priority
GET /api/tasks?sortBy=date
GET /api/tasks?sortBy=status
```

### Add Tags
```json
POST /api/tasks/1/tags
["urgent", "backend", "bug-fix"]
```

## Project Structure
```
src/main/java/com/taskmanagement/taskmanager/
├── controllers/       - REST endpoints
├── services/         - Business logic & design patterns
├── models/           - Entities & interfaces
├── repositories/     - Data access
└── dto/             - Data transfer objects
```

## Design Pattern Benefits

- **Factory:** Centralized task creation, easy to add new task types
- **Observer:** Automatic notifications on task changes
- **Strategy:** Flexible sorting without modifying existing code
- **Decorator:** Optional features without bloating base Task class

## Author

Sara Farghali
