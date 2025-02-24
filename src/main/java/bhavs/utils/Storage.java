package bhavs.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import bhavs.tasks.Task;
import bhavs.tasks.TaskList;

/**
 * Handles loading and saving of tasks to a file.
 * Ensures that tasks persist between program runs.
 */
public class Storage {
    private String filePath;
    private TaskList taskList;
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());
    private Parser parser;

    /**
     * Constructs a {@code Storage} object that manages saving and loading tasks.
     * If the file exists, tasks are loaded into the list.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path must not be null or empty";
        this.filePath = filePath;
        this.taskList = new TaskList();
        loadTasksFromFile();
    }

    /**
     * Returns the task list associated with this storage.
     *
     * @return The task list containing all loaded tasks.
     */
    public TaskList getTaskList() {
        assert taskList != null : "Task list should never be null";
        return taskList;
    }

    /**
     * Saves the current list of tasks to the specified file.
     * If the file or its parent directories do not exist, they will be created.
     */
    public void saveTasksToFile() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            LOGGER.severe("Failed to create directory for file: " + filePath);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            assert taskList != null : "Task list cannot be null when saving";
            for (Task task : taskList.getTasks()) {
                assert task != null : "Task cannot be null when saving";
                bw.write(task.toFileFormat());
                bw.newLine();
            }
            LOGGER.info("✅ Tasks successfully saved to file: " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, " Error saving tasks!", e);
        }
    }

    /**
     * Loads tasks from the specified file into the task list.
     * If the file does not exist, a new list is started.
     */
    public void loadTasksFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            LOGGER.info("No previous tasks found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                assert !line.trim().isEmpty() : "Task line should not be empty";
                Task task = getParser().parseTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
            LOGGER.info("Tasks successfully loaded from file.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading tasks!", e);
        }
    }

    /**
     * Creates a new task list and saves it under a different file.
     *
     * @param newFilePath The new file path for storing tasks.
     * @return Confirmation message.
     */
    public String createNewTaskList(String newFilePath) {
        assert newFilePath != null && !newFilePath.trim().isEmpty() : "New file path must not be null or empty";

        this.filePath = newFilePath;
        this.taskList = new TaskList();

        File newFile = new File(newFilePath);
        try {
            if (newFile.createNewFile()) {
                LOGGER.info("New task list created at: " + newFilePath);
            } else {
                LOGGER.warning("Task list file already exists. Overwriting...");
            }
            saveTasksToFile();
            return "New task list created successfully at: " + newFilePath;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating new task list!", e);
            return " Failed to create a new task list!";
        }
    }

    /**
     * Lazy-loads the parser instance to improve efficiency.
     *
     * @return The parser instance.
     */
    private Parser getParser() {
        if (parser == null) {
            parser = new Parser();
        }
        return parser;
    }
}

