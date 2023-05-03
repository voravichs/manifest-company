package mc.manifestcompany.gamelogic;

import mc.manifestcompany.DataType;
import mc.manifestcompany.company.*;
import org.junit.*;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static org.junit.Assert.*;

public class TurnImplTest {
    private TurnImpl turn;
    private Company company;
    private Deque<Company> npcQueue;

    @Before
    public void setUp() throws Exception {
        this.turn = new TurnImpl(30, 100);
        this.company = new UserCompany("company", new CompanyActionImpl());
        Company npc1 = new NPCCompany("NPC1", new NPCActionImpl());
        Company npc2 = new NPCCompany("NPC2", new NPCActionImpl());
        Company npc3 = new NPCCompany("NPC3", new NPCActionImpl());
        this.npcQueue = new ArrayDeque<>();
        this.npcQueue.add(npc1);
        this.npcQueue.add(npc2);
        this.npcQueue.add(npc3);

    }

    @Test
    public void turn() {
        this.turn.turn(3, this.company);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(560, cash);
        assertEquals(Arrays.asList(150, 90, 60), this.company.getFinancials());

    }

    @Test
    public void validCompany() {
        assertTrue(this.turn.validCompany(this.company));
    }

    @Test
    public void boardFull() {
        assertFalse(this.turn.boardFull(100, this.company, this.npcQueue));
        this.company.getStats().put(DataType.TILES, 100);
        assertTrue(this.turn.boardFull(100, this.company, this.npcQueue));
    }

    @Test
    public void winner() {
        assertEquals(null, this.turn.winner(100, this.company, this.npcQueue));
        this.company.getStats().put(DataType.TILES, 50);
        assertEquals(this.company, this.turn.winner(100, this.company, this.npcQueue));
    }
}