package bhavs.tasks;

import bhavs.utils.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages a list of tasks, including adding, deleting, marking, and unmarking tasks.
 * This class interacts with the {@code Storage} class to save task changes.
 */
public class TaskList {

    private List<Task> taskList = new ArrayList<>();
    public Storage storedIn;

    /**
     * Deletes a task from the list based on user input.
     * If the list is empty, it informs the user.
     *
     * @param scanner
     *         Scanner object used to get user input.
     */
    public void deleteTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to delete.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to delete?", taskList.size());
        Task removedTask = taskList.remove(taskIndex);
        storedIn.saveTasksToFile();
        System.out.println("Task removed: " + removedTask);
    }

    /**
     * Marks a task as completed based on user input.
     * If the list is empty, it informs the user.
     *
     * @param scanner
     *         Scanner object used to get user input.
     */
    public void markTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("Your task list is empty.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to mark as done?", taskList.size());
        taskList.get(taskIndex).markAsComplete();
        storedIn.saveTasksToFile();
    }

    /**
     * Marks a task as incomplete based on user input.
     * If the list is empty, it informs the user.
     *
     * @param scanner
     *         Scanner object used to get user input.
     */
    public void unmarkTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to unmark.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to unmark?", taskList.size());
        taskList.get(taskIndex).markAsIncomplete();
        storedIn.saveTasksToFile();
    }

    /**
     * Retrieves a valid task index from the user.
     * If an invalid index is entered, prompts the user to try again.
     *
     * @param scanner
     *         Scanner object used to get user input.
     * @param prompt
     *         The prompt message displayed to the user.
     * @param listSize
     *         The number of tasks in the list.
     *
     * @return The valid task index.
     */
    public int getTaskIndex(Scanner scanner, String prompt, int listSize) {
        System.out.println(prompt);
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        if (index < 0 || index >= listSize) {
            System.out.println("Invalid task number. Please try again.");
            return getTaskIndex(scanner, prompt, listSize);
        }
        return index;
    }

    public void add(Task task) {
        this.taskList.add(task);
    }

    public boolean isEmpty() {
        return this.taskList.isEmpty();
    }

    public int size() {
        return this.taskList.size();
    }

    public Task get(int i) {
        return this.taskList.get(i);
    }

    public List<Task> getTasks() {
        return this.taskList;
    }

    public void findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }
    }
}
