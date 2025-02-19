package bhavs.tasks;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents a task with a description and a completion status.
 * This class serves as a base for different types of tasks.
 */
public class Task {
    protected String description;

    protected boolean isDone;

    /**
     * Constructs a new task with the given description.
     * The task is initially marked as incomplete.
     *
     * @param description
     *         The description of the task.
     */

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a task with the given description and completion status.
     * Used when loading tasks from storage.
     *
     * @param description
     *         The description of the task.
     * @param isDone
     *         Whether the task is completed (true) or not (false).
     */

    public Task(String description, boolean isDone) { // Constructor for loading tasks
        this.description = description;
        this.isDone = isDone;
    }

    public String getDescription() {
        return this.description;
    }

    public void markAsComplete() {
        this.isDone = true;
    }

    public void markAsIncomplete() {
        this.isDone = false;
    }

    public boolean isCompleted() {
        return isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }




    /**
     * Converts the task to a file-friendly format for saving.
     *
     * @return A string representing the task in a format suitable for file storage.
     */
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string indicating task completion status and description.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.empty();
    }
}
