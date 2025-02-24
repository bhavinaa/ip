package bhavs.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Manages a list of tasks, including adding, deleting, marking, and unmarking tasks.
 */
public class TaskList {

    private final List<Task> tasks;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    public void sortTasks() {
        tasks.sort(Comparator.comparing(
                task -> task.getDateTime().orElse(LocalDateTime.MAX)
        ));
    }


    /**
     * Retrieves a task by index.
     *
     * @param index The task index.
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The deleted task, or null if the index is invalid.
     */
    public Task deleteTask(int index) {
        if (isValidIndex(index)) {
            return tasks.remove(index);
        }
        return null;
    }

    /**
     * Marks a task as completed.
     *
     * @param index The index of the task to mark as done.
     * @return The updated task, or null if the index is invalid.
     */
    public Task markTask(int index) {
        if (isValidIndex(index)) {
            Task task = tasks.get(index);
            task.markAsDone();
            return task;
        }
        return null;
    }

    /**
     * Unmarks a completed task.
     *
     * @param index The index of the task to unmark.
     * @return The updated task, or null if the index is invalid.
     */
    public Task unmarkTask(int index) {
        if (isValidIndex(index)) {
            Task task = tasks.get(index);
            task.markAsNotDone();
            return task;
        }
        return null;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves all tasks in the list.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks); // Return a copy to prevent modification outside
    }

    /**
     * Returns a formatted list of all tasks.
     *
     * @return A string representation of all tasks.
     */
    public String getAllTasks() {
        if (tasks.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks if an index is within valid bounds.
     *
     * @param index The index to check.
     * @return True if the index is valid, false otherwise.
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }
}
