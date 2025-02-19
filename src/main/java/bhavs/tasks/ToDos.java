package bhavs.tasks;
import java.util.Optional;
import java.time.LocalDateTime;

public class ToDos extends Task {
    public ToDos(String description) {
        super(description);
    }

    // Add a second constructor for loading from file
    public ToDos(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.empty();
    }
}
