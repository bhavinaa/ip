public class Parser {



    public Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.out.println("Skipping corrupted task entry: " + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                return new ToDos(description, isDone);
            case "D":
                return new Deadlines(description, isDone, parts[3]);
            case "E":
                return new Events(description, isDone, parts[3], parts[4]);
            default:
                return null;
        }
    }
}
