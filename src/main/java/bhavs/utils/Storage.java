package bhavs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import bhavs.tasks.Task;
import bhavs.tasks.TaskList;
import java.util.logging.Level;
import java.util.logging.Logger;


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
     * Constructs a {@code Storage} object that manages the saving and loading of tasks.
     * If the file exists, tasks are loaded into the list.
     *
     * @param filePath The absolute or relative path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path must not be null or empty";
        this.filePath = filePath;
        this.taskList = new TaskList();
        loadTasksFromFile();
        git 
        // assert false : "Assertions are working!";

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
     * Logs an error message if saving fails.
     */
    public void saveTasksToFile() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            assert created || parentDir.exists() : "Failed to create directory for file: " + filePath;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            assert taskList != null : "Task list cannot be null when saving";
            for (Task task : taskList.getTasks()) {
                assert task != null : "Task cannot be null when saving";
                bw.write(task.toFileFormat());
                bw.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving tasks", e);
        }
    }

    /**
     * Loads tasks from the specified file into the task list.
     * If the file does not exist, a new list is started.
     * Logs an error message if loading fails.
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
                assert task != null : "Failed to parse task from line: " + line;
                if (task != null) {
                    taskList.add(task);
                }
            }
            LOGGER.info("Tasks successfully loaded from file.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading tasks", e);
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
        assert parser != null : "Parser should be initialized before returning";
        return parser;
    }
}
