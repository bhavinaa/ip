public class Task {
    String task_given;
    boolean task_completed = false;

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

    public boolean isCompleted(){
        return task_completed;
    }

    public String get_task() {
        return task_given;
    }

    public void markAsComplete(){
        task_completed = true;
    }

    public void markAsIncomplete() {
        task_completed = false;
    }

    public String toString() {
        return "[" + this.get_status() + "] " + task_given;
    }
}
