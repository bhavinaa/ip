package bhavs.tasks;

import bhavs.utils.Time;
import java.util.Optional;
import java.time.LocalDateTime;

public class Deadlines extends Task {
    private Time deadline;

    // Constructor for user input (interactive mode)
    public Deadlines(String description, String deadline) {
        super(description);

        Time parsedDeadline = new Time(deadline, true);

        // Validate the deadline before assigning
        if (parsedDeadline.getLocalDateTime() == null) {
            System.out.println("ERROR: Invalid deadline format! Please use HH:mm (24-hour format).");
            this.deadline = null; // Set to null to indicate invalid input
        } else {
            this.deadline = parsedDeadline;
        }
    }

    // Constructor for loading from file (non-interactive)
    public Deadlines(String description, boolean isDone, String deadline) {
        super(description, isDone);

        Time parsedDeadline = new Time(deadline);

        // Validate the deadline before assigning
        if (parsedDeadline.getLocalDateTime() == null) {
            System.out.println("ERROR: Invalid deadline found in file! Skipping task.");
            this.deadline = null; // Set to null to indicate invalid data
        } else {
            this.deadline = parsedDeadline;
        }
    }

    public Time getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + (deadline != null ? deadline : "INVALID") + ")";
    }

    @Override
    public String toFileFormat() {
        if (deadline == null) {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | INVALID";
        }
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + deadline.toFileFormat();
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(deadline != null ? deadline.getLocalDateTime() : null);
    }
}
