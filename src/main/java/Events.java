class Events extends Task {
    private Time start;
    private Time end;

    // Constructor for user input (interactive mode)
    public Events(String description, String start, String end) {
        super(description);
        this.start = new Time(start, true); // Ask user until valid input
        this.end = new Time(end, true);
    }

    // Constructor for loading from file (non-interactive)
    public Events(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        this.start = new Time(start); // Load directly from file
        this.end = new Time(end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    // ✅ Convert event task to file format (for saving)
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start.toFileFormat() + " | " + end.toFileFormat();
    }
}
