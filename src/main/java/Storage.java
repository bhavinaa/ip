import java.io.*;


public class Storage {

    private String filePath;
    public TaskList taskList;

    private Parser parser = new Parser();
    public Storage(String filePath, TaskList taskList) {
        this.filePath = filePath;
        this.taskList = taskList;
        this.taskList.storedIn = this;

    }

    public void saveTasksToFile() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Task task : taskList.getTasks()) {
                bw.write(task.toFileFormat());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
    public void loadTasksFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No previous tasks found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = parser.parseTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }


}
