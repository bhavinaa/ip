package bhavs.tasks;

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
}
