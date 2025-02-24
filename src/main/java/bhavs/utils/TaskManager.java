
package bhavs.utils;

import bhavs.tasks.Task;
import bhavs.tasks.TaskList;
import bhavs.tasks.Events;
import bhavs.tasks.Deadlines;
import bhavs.tasks.ToDos;
import java.util.logging.Logger;

/**
 * Manages task-related operations including adding, marking, unmarking, and deleting tasks.
 */
public class TaskManager {
    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());

    private final TaskList taskList;
    private final Storage storage;
    private final CommandProcessor commandProcessor;

    public TaskManager(Storage storage, TaskList taskList) {
        assert storage != null : "Storage instance cannot be null";
        assert taskList != null : "TaskList instance cannot be null";

        this.taskList = taskList;
        this.storage = storage;
        this.commandProcessor = new CommandProcessor(this, this.storage);
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public Storage getStorage() {
        return storage;
    }

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    /**
     * Loads tasks from the storage file into memory.
     */
    public void loadTasks() {
        storage.loadTasksFromFile();
    }

    /**
     * Marks a task as completed.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    public String markTask(String argument) {
        return modifyTaskStatus(argument, true);
    }

    /**
     * Unmarks a completed task.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    public String unmarkTask(String argument) {
        return modifyTaskStatus(argument, false);
    }

    private String modifyTaskStatus(String argument, boolean markAsDone) {
        try {
            int index = Integer.parseInt(argument) - 1;
            if (markAsDone) {
                taskList.markTask(index);
            } else {
                taskList.unmarkTask(index);
            }
            storage.saveTasksToFile();
            return (markAsDone ? "Nice! I've marked this task as done:\n" : "OK! I've unmarked this task:\n")
                    + taskList.get(index);
        } catch (NumberFormatException e) {
            return "Invalid task number! Use 'mark <number>' or 'unmark <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range! There are only " + taskList.size() + " tasks.";
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    public String deleteTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            Task removed = taskList.get(index);
            taskList.deleteTask(index);
            storage.saveTasksToFile();
            return "Noted. I've removed this task:\n" + removed +
                    "\n You have " + taskList.size() + " tasks left!";
        } catch (NumberFormatException e) {
            return "Invalid task number! Use 'delete <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range! There are only " + taskList.size() + " tasks.";
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
        if (newTask == null) {
            return getFormattingMessage();
        }
        taskList.add(newTask);
        storage.saveTasksToFile();
        return "Got it! I've added this task:\n" + newTask;
    }


    // USED CHATGPT - TO WRITE COMMANDS BETTER
    /**
     * Provides formatting guidance for adding tasks.
     *
     * @return The task formatting instructions.
     */
    public String getFormattingMessage() {
        return "⚠️ Invalid task format! Please use one of the following formats:\n\n"
                + "📝 **To add a ToDo:** `task description`\n"
                + "   Example: `Buy groceries`\n\n"
                + "📅 **To add an Event:** `event description, start time, end time`\n"
                + "   Example: `Team meeting, 2025-05-04 18:00, 2025-10-04 08:00`\n\n"
                + "⏳ **To add a Deadline:** `task description, deadline`\n"
                + "   Example: `Submit report, 2025-05-03 18:00`\n\n"
                + "🕒 **Time Format:** Use 24-hour format (HH:mm)!\n"
                + "❌ Invalid: `99:99` or `25:30`\n"
                + "✅ Valid: `09:30` or `18:45`\n\n"
                + "🔁 Please try again using the correct format!";
    }

    /**
     * Creates a task from user input.
     *
     * @param input The task description and details.
     * @return The created Task object, or null if input is invalid.
     */
    private Task createTask(String input) {
        assert input != null && !input.trim().isEmpty() : "Task input cannot be null or empty";
        String[] parts = input.split(",\\s*");

        if (parts.length == 3) {  // Event Task
            Events eventTask = new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
            return (eventTask.getStart() != null && eventTask.getEnd() != null) ? eventTask : null;
        } else if (parts.length == 2) {  // Deadline Task
            Deadlines deadlineTask = new Deadlines(parts[0].trim(), parts[1].trim());
            return deadlineTask.getDeadline() != null ? deadlineTask : null;
        } else if (parts.length == 1) {  // ToDo Task
            return new ToDos(parts[0].trim());
        }
        return null;
    }

    /**
     * Displays the list of tasks.
     *
     * @return The formatted task list.
     */
    public String displayTasks() {
        if (taskList.isEmpty()) {
            return "📭 Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("📋 **Here are your tasks:**\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }
}


