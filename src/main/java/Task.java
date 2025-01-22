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
}
