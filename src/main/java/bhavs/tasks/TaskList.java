package bhavs.tasks;

import bhavs.utils.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskList {

    private List<Task> taskList = new ArrayList<>();
    public Storage storedIn;

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

    public void markTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("Your task list is empty.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to mark as done?", taskList.size());
        taskList.get(taskIndex).markAsComplete();
        storedIn.saveTasksToFile();
    }

    public void unmarkTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to unmark.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to unmark?", taskList.size());
        taskList.get(taskIndex).markAsIncomplete();
        storedIn.saveTasksToFile();
    }

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
}
