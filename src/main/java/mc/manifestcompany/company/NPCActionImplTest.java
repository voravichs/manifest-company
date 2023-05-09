package mc.manifestcompany.company;

import mc.manifestcompany.DataType;
import mc.manifestcompany.company.NPCActionImpl;
import mc.manifestcompany.company.NPCCompany;
import mc.manifestcompany.gamelogic.Event;
import mc.manifestcompany.gui.Tile;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NPCActionImplTest {
    private NPCActionImpl npcAction;
    private NPCCompany company;
    private Tile[][] grid;

    @Before
    public void setUp() {
        npcAction = new NPCActionImpl();
        company = new NPCCompany("Test Company", npcAction, Tile.TileType.CLAIMED_P2, "images/npc_image.png");
        grid = new Tile[20][20];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Tile(
                        new Rectangle(20, 20),
                        new Point2D(i * 20, j * 20),
                        Tile.TileType.EMPTY);
            }
        }
    }

    @Test
    public void testInvest() {
        try {
            npcAction.invest(100, "Marketing", company);
            npcAction.invest(100, "R&D", company);
            npcAction.invest(100, "Goods", company);
            npcAction.invest(100, "HR", company);
            // No need to check the results since we're only testing if the code runs without errors
        } catch (Exception e) {
            fail("An exception occurred while running invest: " + e.getMessage());
        }
    }

    @Test
    public void testTiles() {
        try {
            npcAction.tiles(1, "Purchase", company, grid);
            npcAction.tiles(-1, "Sell", company, grid);
            // No need to check the results since we're only testing if the code runs without errors
        } catch (Exception e) {
            fail("An exception occurred while running tiles: " + e.getMessage());
        }
    }

    @Test
    public void testPerformRandomAction() {
        try {
            int initialCash = company.getStats().get(DataType.CASH);
            npcAction.performRandomAction(company, grid, Event.EventType.EXPANSION);
            int newCash = company.getStats().get(DataType.CASH);
            // No need to check the results since we're only testing if the code runs without errors
        } catch (Exception e) {
            fail("An exception occurred while running performRandomAction: " + e.getMessage());
        }
    }
}
