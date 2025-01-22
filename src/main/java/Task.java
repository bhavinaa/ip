public class Task {
    String task_given;
    Boolean task_completed = false;

    Task(String task){
        task_given = task;
    }

    public String get_status() {
        if(task_completed) {
            return "X";
        } else {
            return " ";
        }
    }

    public Boolean is_completed(){
        return task_completed;
    }

    public String get_task() {
        return task_given;
    }

    public void mark_as_complete(){
        task_completed = true;
    }

    public void mark_as_incomplete() {
        task_completed = false;
    }

    public String toString() {
        return "[" + this.get_status() + "] " + task_given;
    }
}
