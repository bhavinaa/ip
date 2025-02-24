package bhavs.tasks;

import bhavs.utils.Time;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A type of task that includes both start and end times.
 */
public class Events extends Task {
    private Time start;
    private Time end;

    // Constructor for user input (interactive mode)
    public Events(String description, String start, String end) {
        super(description);

        Time startTime = new Time(start, true);
        Time endTime = new Time(end, true);

        // Validate time before assigning
        if (startTime.getLocalDateTime() == null || endTime.getLocalDateTime() == null) {
            System.out.println("ERROR: Invalid time format for event!");
            this.start = null;
            this.end = null;
        } else {
            this.start = startTime;
            this.end = endTime;
        }
    }

    // Constructor for loading from file (non-interactive)
    public Events(String description, boolean isDone, String start, String end) {
        super(description, isDone);

        Time startTime = new Time(start);
        Time endTime = new Time(end);

        // Validate time before assigning
        if (startTime.getLocalDateTime() == null || endTime.getLocalDateTime() == null) {
            System.out.println("ERROR: Invalid time format when loading from file!");
            this.start = null;
            this.end = null;
        } else {
            this.start = startTime;
            this.end = endTime;
        }
    }

    public Time getStart() {
        return this.start;
    }

    public Time getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + (start != null ? start : "INVALID") + " to: " + (end != null ? end : "INVALID") + ")";
    }

    @Override
    public String toFileFormat() {
        if (start == null || end == null) {
            return "E | " + (isDone ? "1" : "0") + " | " + description + " | INVALID | INVALID";
        }
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start.toFileFormat() + " | " + end.toFileFormat();
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(start != null ? start.getLocalDateTime() : null);
    }
}
