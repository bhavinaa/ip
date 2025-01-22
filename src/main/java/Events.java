public class Events extends Task {

    String startTime;
    String endTime;
    String item;

    Events(String item, String start_time, String end_time) {
        super(item);
        this.item = item;
        this.startTime = start_time;
        this.endTime = end_time;
    }

    @Override
    public String toString() {
        boolean completed_status = super.isCompleted();
        String status = completed_status? "[x]": "[]";
        return "[E]" + super.toString()+  "(from: " + startTime + "to: " + endTime + ")";
    }
}
