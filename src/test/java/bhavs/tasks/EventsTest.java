package bhavs.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventsTest {

    @Test
    public void testConstructor_InteractiveMode() {
        Events event = new Events("Team meeting", "2024-02-01 1000", "2024-02-01 1200");
        assertEquals("Team meeting", event.getDescription());
    }

    @Test
    public void testConstructor_FileLoadMode() {
        Events event = new Events("Hackathon", true, "2024-05-10 08:00", "2024-05-10 18:00");
        assertEquals("Hackathon", event.getDescription());
        assertTrue(event.isCompleted()); 
    }
}
