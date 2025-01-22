public class Deadlines extends Task {

    String deadline;
    Deadlines(String task, String time) {
        super(task);
        deadline = time;
    }

    @Override
    public String toString() {
        return super.toString() + "(by: " + deadline + ")";
    }
}
