package mc.manifestcompany.gamelogic;

import mc.manifestcompany.DataType;
import mc.manifestcompany.company.*;
import mc.manifestcompany.gui.Tile;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class TurnImplTest {
    private TurnImpl turn;
    private Company company;
    private Deque<Company> npcQueue;
    private List<Company> companyList;

    @Before
    public void setUp() throws Exception {
        this.turn = new TurnImpl(30, 100);
        this.company = new UserCompany("Player", new CompanyActionImpl(), Tile.TileType.CLAIMED_P1, "images/playerfastfood.png");
        Company npc1 = new NPCCompany("WacMondalds", new NPCActionImpl(), Tile.TileType.CLAIMED_P2, "images/wacmonalds.png");
        Company npc2 = new NPCCompany("Queso Queen", new NPCActionImpl(), Tile.TileType.CLAIMED_P3, "images/quesoqueen.png");
        Company npc3 = new NPCCompany("Pizza Shack", new NPCActionImpl(), Tile.TileType.CLAIMED_P4, "images/pizzashack.png");
        this.npcQueue = new ArrayDeque<>();
        this.npcQueue.add(npc1);
        this.npcQueue.add(npc2);
        this.npcQueue.add(npc3);
        this.companyList = new ArrayList<>();
        this.companyList.addAll(Arrays.asList(company, npc1, npc2, npc3));
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
        assertNotNull(this.turn.winner(100, this.companyList));
        this.company.getStats().put(DataType.TILES, 50);
        assertEquals(this.company, this.turn.winner(100, this.companyList));
    }
}