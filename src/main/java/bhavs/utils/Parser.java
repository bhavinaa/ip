package bhavs.utils;


import bhavs.tasks.Deadlines;
import bhavs.tasks.Events;
import bhavs.tasks.Task;
import bhavs.tasks.ToDos;



/**
 * * Parses task data from a stored file format and converts it into task objects.
 * Supports parsing of To-Do, Deadline, and Event tasks.
 */
public class Parser {

    /**
     * Parses a task from a given line of text, converting it into the appropriate task object.
     * If the task entry is corrupted or invalid, it prints a warning message and returns {@code null}.
     *
     * @param line The line of text representing the task in the stored file format.
     * @return The parsed {@code Task} object, or {@code null} if the input is invalid.
     */

    public Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.out.println("Skipping corrupted task entry: " + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        switch (type) {
            case "T":
                return new ToDos(description, isDone);
            case "D":
                return new Deadlines(description, isDone, parts[3]);
            case "E":
                return new Events(description, isDone, parts[3], parts[4]);
            default:
                return null;
        }
    }
}
