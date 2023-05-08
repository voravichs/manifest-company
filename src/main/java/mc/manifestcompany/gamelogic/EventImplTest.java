package mc.manifestcompany.gamelogic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventImplTest {
    private Event event;
    @Before
    public void setUp() throws Exception {
        event = new EventImpl();
    }

    @Test
    public void randomEvent() {
        for (int i = 0; i < 5; i++) {
            Event.EventType eventType = event.randomEvent();
            System.out.println(eventType);
        }
    }

    @Test
    public void updateMarket() {
        int [] updated;
        updated = event.updateMarket(Event.EventType.NONE, 30, 100);
        assertEquals(30, updated[0]);
        assertEquals(100, updated[1]);

        updated = event.updateMarket(Event.EventType.PANDEMIC, 30, 100);
        assertEquals(15, updated[0]);
        assertEquals(100, updated[1]);

        updated = event.updateMarket(Event.EventType.EXPANSION, 30, 100);
        assertEquals(60, updated[0]);
        assertEquals(200, updated[1]);

        updated = event.updateMarket(Event.EventType.RECESSION, 30, 100);
        assertEquals(15, updated[0]);
        assertEquals(50, updated[1]);

        updated = event.updateMarket(Event.EventType.COMPETITION, 30, 100);
        assertEquals(60, updated[0]);
        assertEquals(25, updated[1]);

        updated = event.updateMarket(Event.EventType.INNOVATION, 30, 100);
        assertEquals(30, updated[0]);
        assertEquals(200, updated[1]);

        updated = event.updateMarket(Event.EventType.WAR, 30, 100);
        assertEquals(10, updated[0]);
        assertEquals(10, updated[1]);

        updated = event.updateMarket(Event.EventType.RECESSION, 1, 1);
        assertEquals(1, updated[0]);
        assertEquals(1, updated[1]);

    }
}
