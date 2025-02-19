package bhavs.tasks;

import bhavs.utils.Time;
import java.util.Optional;
import java.time.LocalDateTime;


public class Deadlines extends Task {
    private Time deadline;

    // Constructor for user input (interactive mode)
    public Deadlines(String description, String deadline) {
        super(description);
        this.deadline = new Time(deadline, true);
    }

    // Constructor for loading from file (non-interactive)
    public Deadlines(String description, boolean isDone, String deadline) {
        super(description, isDone);
        this.deadline = new Time(deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + deadline.toFileFormat();
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(deadline.getLocalDateTime());
    }
}
