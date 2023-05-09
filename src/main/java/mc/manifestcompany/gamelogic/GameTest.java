package mc.manifestcompany.gamelogic;

import javafx.scene.shape.Rectangle;
import mc.manifestcompany.DataType;
import mc.manifestcompany.FileHandler;
import mc.manifestcompany.company.Company;
import mc.manifestcompany.gui.Tile;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void init() {
        Game game = new Game(20,20,
                "test","./companies/fastFood.txt");

        // TileGrid Dimensions
        assertEquals(20, game.getTileGrid().length);
        assertEquals(20, game.getTileGrid()[0].length);
        assertEquals(1, game.getTurnNum());

        // Companies
        assertEquals("test", game.getPlayer().getName());
        assertEquals(4, game.getCompanyList().size());

        List<String> npcNames = new ArrayList<>(
                Arrays.asList("WacMondalds", "Queso Queen", "Pizza Shack"));
        for (Company npcCompany: game.getNPCs()) {
            assertTrue(npcNames.contains(npcCompany.getName()));
        }
    }

    @Test
    public void nextTurn() {
        Game game = new Game(20,20,
                "test","./companies/fastFood.txt");

        // advancing a turn is random, so it's difficult to test properly
        // testing to make sure there's no errors
        game.nextTurn(game.getTileGrid());
        // Bc setting turn is done by the gui, we do it manually here
        game.setTurnNum(game.getTurnNum() + 1);
        assertEquals(2, game.getTurnNum());
    }

    @Test
    public void sort() throws IOException {
        Game game = FileHandler.load("turn5.txt");

        // Highest tiles is player from this game
        List<Company> companyList = game.sortCompaniesBy(DataType.TILES);
        assertEquals(Tile.TileType.CLAIMED_P1, companyList.get(0).getTileType());

        // Highest cash is WacMonalds from this game
        companyList = game.sortCompaniesBy(DataType.CASH);
        assertEquals("Pizza Shack", companyList.get(0).getName());
    }

    @Test
    public void enumsAndOtherStuff() {
        Game.GameOverState gameOver = Game.GameOverState.PLAYER_BANKRUPT;
        Event.EventType event = Event.EventType.NONE;
        Game game = new Game(20,20,
                "test","./companies/fastFood.txt");

        Rectangle square = game.getTileGrid()[0][0].getSquare();
        assertNotNull(square);
    }
}
