package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.company.Company;
import mc.manifestcompany.gamelogic.Game;
import mc.manifestcompany.gui.Tile;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileHandlerTest {
    @Test
    public void load() throws IOException {
        Game loadedGame = FileHandler.load("newGame.txt");
        Tile[][] tileGrid = loadedGame.getTileGrid();
        List<Company> companyList = loadedGame.getCompanyList();
        int turnNum = loadedGame.getTurnNum();

        // Create new grid
        Tile[][] expectedTileGrid = new Tile[20][20];
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                expectedTileGrid[x][y] = new Tile(
                        new Rectangle(20,20),
                        new Point2D(x * 20, y * 20),
                        Tile.TileType.EMPTY);
            }
        }
        expectedTileGrid[0][0].setType(Tile.TileType.CLAIMED_P1);
        expectedTileGrid[0][19].setType(Tile.TileType.CLAIMED_P2);
        expectedTileGrid[19][0].setType(Tile.TileType.CLAIMED_P3);
        expectedTileGrid[19][19].setType(Tile.TileType.CLAIMED_P4);

        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                assertEquals(expectedTileGrid[i][j].getType(), tileGrid[i][j].getType());
            }
        }

        List<String> expectedCompanyNameList = new ArrayList<>(
                Arrays.asList("WacMondalds","Pizza Shack", "Queso Queen", "Dee's Nuts"));

        List<String> actualCompanyNameList = new ArrayList<>();
        for (Company company: companyList) {
            actualCompanyNameList.add(company.getName());
        }
        assertEquals(expectedCompanyNameList, actualCompanyNameList);

        assertEquals(1, turnNum);
    }
    @Test
    public void save() throws IOException {
        // Get a game
        Game loadedGame = FileHandler.load("turn5.txt");
        Tile[][] tileGrid = loadedGame.getTileGrid();
        List<Company> companyList = loadedGame.getCompanyList();
        int turnNum = loadedGame.getTurnNum();
        List<Integer> marketValues = loadedGame.getMarketValues();
        // save the game
        FileHandler.save(
                tileGrid,
                companyList,
                turnNum,
                marketValues,
                "turn5test");

        // Load the file again to make sure the game save correctly
        loadedGame = FileHandler.load("turn5test.txt");

        // Every tile matches
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[i].length; j++) {
                assertEquals(loadedGame.getTileGrid()[i][j].getType(), tileGrid[i][j].getType());
            }
        }

        for (int i = 0; i < companyList.size(); i++) {
            Company prevCompany = companyList.get(i);
            Company savedCompany = loadedGame.getCompanyList().get(i);
            assertEquals(prevCompany.getName(), savedCompany.getName());
            assertEquals(prevCompany.getImageLink(), savedCompany.getImageLink());
            assertEquals(prevCompany.getStats(), savedCompany.getStats());
            assertEquals(prevCompany.getTileType(), savedCompany.getTileType());
        }

        assertEquals(turnNum, loadedGame.getTurnNum());

        assertEquals(marketValues, loadedGame.getMarketValues());
    }
}
