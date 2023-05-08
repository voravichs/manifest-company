package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gui.Tile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanyActionImplTest {

    private Company company;

    private CompanyAction actions;

    private Tile[][] grid;
    @Before
    public void setUp() throws Exception {
        this.actions = new CompanyActionImpl();
        this.company = new UserCompany("company", this.actions,
                Tile.TileType.CLAIMED_P1,"images/playerfastfood.png");
        this.grid = new Tile[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = new Tile(
                        new Rectangle(20,20),
                        new Point2D(i * 20, j * 20),
                        Tile.TileType.EMPTY);
            }
        }

    }

    @Test
    public void invest() {
        this.actions.invest(100, "Marketing", this.company);
        int multiplier = this.company.getStats().get(DataType.MULTIPLIER);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(3, multiplier);
        assertEquals(400, cash);

        this.actions.invest(100, "R&D", this.company);
        int price = this.company.getStats().get(DataType.PRICE);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(70, price);
        assertEquals(300, cash);


        this.actions.invest(100, "Goods", this.company);
        int capacity = this.company.getStats().get(DataType.CAPACITY);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(17, capacity);
        assertEquals(200, cash);

        this.actions.invest(100, "HR", this.company);
        int cost = this.company.getStats().get(DataType.COST);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(24, cost);
        assertEquals(100, cash);
    }

    @Test
    public void tiles() {
        this.actions.tiles(1, "Purchase", this.company, grid);
        int tiles = this.company.getStats().get(DataType.TILES);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(2, tiles);
        assertEquals(200, cash);

        this.actions.tiles(-1, "Sell", this.company, grid);
        tiles = this.company.getStats().get(DataType.TILES);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(1, tiles);
        assertEquals(500, cash);

    }
}