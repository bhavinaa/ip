package bhavs.utils;

import bhavs.tasks.Task;
import bhavs.tasks.TaskList;

import java.io.*;

/**
 * Handles loading and saving of tasks to a file.
 * Ensures that tasks persist between program runs.
 */
public class Storage {

    private String filePath;
    public TaskList taskList;

    private Parser parser = new Parser();

    /**
     * Constructs a {@code Storage} object that manages the saving and loading of tasks.
     *
     * @param filePath The path to the file where tasks are stored.
     * @param taskList The task list that holds tasks in memory.
     */
    public Storage(String filePath, TaskList taskList) {
        this.filePath = filePath;
        this.taskList = taskList;
        this.taskList.storedIn = this;

    }

    /**
     * Saves the current list of tasks to the specified file.
     * If the file or its parent directories do not exist, they will be created.
     * Prints an error message if saving fails.
     */

    public void saveTasksToFile() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Task task : taskList.getTasks()) {
                bw.write(task.toFileFormat());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the specified file into the task list.
     * If the file does not exist, a new list is started.
     * Prints an error message if loading fails.
     */
    public void loadTasksFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No previous tasks found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = parser.parseTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
