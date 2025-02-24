package bhavs.tasks;

import bhavs.utils.Time;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A type of tasks that includes two start and end times
 * two constructors is needed to initalise this as the format of the input from hte user is different from
 * the way it is read from the file
 **/

public class Events extends Task {
    private Time start;
    private Time end;

    // Constructor for user input (interactive mode) (THIS IS WHEN you are taking in user input)
    public Events(String description, String start, String end) {
        super(description);
        this.start = new Time(start, true); // Ask user until valid input
        this.end = new Time(end, true);

        // if (this.start.getLocalDateTime() == null) {
        //     throw new IllegalArgumentException(this.start.getErrorMessage());
        // }
        //
        // if (this.end.getLocalDateTime() == null) {
        //     throw new IllegalArgumentException(this.end.getErrorMessage());
        // }
    }

    // Constructor for loading from file (non-interactive)
    // you need to reinitalise the taskobject. during this time, you cannot ask for the user INPUT again
    public Events(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        this.start = new Time(start); // Load directly from file
        this.end = new Time(end);

        // if (this.start.getLocalDateTime() == null) {
        //     throw new IllegalArgumentException(this.start.getErrorMessage());
        // }
        //
        // if (this.end.getLocalDateTime() == null) {
        //     throw new IllegalArgumentException(this.end.getErrorMessage());
        // }
    }

    public Time getStart(){
        return this.start;
    }

    public Time getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    // Convert event task to file format (for saving)
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start.toFileFormat() + " | " + end.toFileFormat();
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(start.getLocalDateTime());
    }
}
