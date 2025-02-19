package bhavs.utils;

import bhavs.tasks.*;
import bhavs.utils.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles operations related to task management.
 * Manages adding, marking, unmarking, and deleting tasks.
 */
public class TaskManager {
    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());

    private final TaskList taskList;
    private final Storage storage;
    private final CommandProcessor commandProcessor;

    public TaskManager(Storage storage, TaskList taskList) {
        assert storage!= null: "Storage instance cannot be null";
        assert taskList != null: "TaskList instance cannot be null";

        this.taskList = taskList;
        this.storage = storage;
        this.commandProcessor = new CommandProcessor(this, this.storage);

    }

    public TaskList getTaskList() {
        return this.taskList;
    }

    /**
     * Loads tasks from the storage file into memory.
     */
    public void loadTasks() {
        storage.loadTasksFromFile();
    }

    /**
     * Retrieves the storage instance.
     *
     * @return The storage instance.
     */
    public Storage getStorage() {
        return this.storage;
    }

    public CommandProcessor getCommandProcessor() {
        return this.commandProcessor;
    }

    /**
     * Marks a task as completed.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    public String markTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            assert index >= 0 && index < taskList.size() : "Task index out of bounds: " + index;
            taskList.markTask(index);
            storage.saveTasksToFile();
            return "Nice! I've marked this task as done:\n" + taskList.get(index);
        } catch (NumberFormatException e) {
            return "Invalid task number. Use 'mark <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range!";
        }
    }

    /**
     * Unmarks a completed task.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    public String unmarkTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            taskList.unmarkTask(index);
            storage.saveTasksToFile();
            return "OK! I've unmarked this task:\n" + taskList.get(index);
        } catch (NumberFormatException e) {
            return "Invalid task number. Use 'unmark <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range!";
        }
    }

    public String deleteTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            assert !taskList.isEmpty() : "Cannot delete from an empty task list";
            assert index >= 0 && index < taskList.size() : "Invalid index for deletion: " + index;

            Task removed = taskList.get(index);
            taskList.deleteTask(index);
            storage.saveTasksToFile();
            return "Noted. I've removed this task:\n" + removed;
        } catch (NumberFormatException e) {
            return "Invalid task number. Use 'delete <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range!";
        }
    }
    /**
     * Adds a new task to the list.
     *
     * @param input The task description.
     * @return The response message.
     */
    public String addTask(String input) {
        Task newTask = createTask(input);
        if (newTask != null) {
            taskList.add(newTask);
            storage.saveTasksToFile();
            return "Got it. I've added this task:\n" + newTask;
        }
        return "Invalid task format. Try again.";
    }

    /**
     * Creates a task from user input.
     *
     * @param input The task description and details.
     * @return The created Task object.
     */
    private Task createTask(String input) {
        assert input != null && !input.trim().isEmpty() : "Task input cannot be null or empty";
        Task newTask = null;
        String[] parts = input.split(",\\s*");
        if (parts.length == 3) {
            newTask = new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } else if (parts.length == 2) {
            newTask =  new Deadlines(parts[0].trim(), parts[1].trim());
        } else if (parts.length == 1) {
            newTask =  new ToDos(parts[0].trim());
        }

        assert newTask != null : "Failed to create task from input: " + input;
        return newTask;
    }


    /**
     * Displays the list of tasks.
     *
     * @return The formatted task list.
     */
    public String displayTasks() {
        assert taskList != null : "Task list is null while displaying";
        if (taskList.isEmpty()) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < taskList.size(); i++) {
            assert taskList.get(i) != null : "Null task found in list at index " + i;
            sb.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }
}
