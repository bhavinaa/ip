package bhavs.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testConstructor_NewTaskIsIncomplete() {
        Task task = new Task("CS2103T WK3");
        assertFalse(task.isCompleted());
        assertEquals("CS2103T WK3", task.description);
    }

    @Test
    public void testConstructor_TaskLoadedFromFile() {
        Task task = new Task("CS2109S", true);
        assertTrue(task.isCompleted());
        assertEquals("CS2109S", task.description);
    }

    @Test
    public void testMarkAsComplete() {
        Task task = new Task("Attend Lecture");
        assertFalse(task.isCompleted());
        task.markAsComplete();
        assertTrue(task.isCompleted());
    }

    @Test
    public void testMarkAsIncomplete() {
        Task task = new Task("Finish CS2103T IP", true);
        assertTrue(task.isCompleted());
        task.markAsIncomplete();
        assertFalse(task.isCompleted());
    }

    @Test
    public void testToFileFormat() {
        Task task = new Task("make portfolio website", false);
        assertEquals("0 | make portfolio website", task.toFileFormat());

        Task completedTask = new Task("Submit assignment", true);
        assertEquals("1 | Submit assignment", completedTask.toFileFormat());
    }

    @Test
    public void testToString() {
        Task task = new Task("Read Notes", false);
        assertEquals("[ ] Read Notes", task.toString());

        Task completedTask = new Task("Buy Crypto", true);
        assertEquals("[X] Buy Crypto", completedTask.toString());
    }
}

