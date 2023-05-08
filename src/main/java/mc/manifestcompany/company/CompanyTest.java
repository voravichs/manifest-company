package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.gui.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class CompanyTest {

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
    public void getTileStack() {

        this.actions.tiles(1 , "Purchase", this.company, this.grid);
        Stack<Point2D> tileStack = this.company.getTileStack();
        assertEquals(tileStack.size(), 1);

    }

    @Test
    public void addToStack() {
        this.actions.tiles(1 , "Purchase", this.company, this.grid);
        Stack<Point2D> tileStack = this.company.getTileStack();
        assertEquals(tileStack.size(), 1);

    }

    @Test
    public void popFromStack() {
        this.actions.tiles(1 , "Purchase", this.company, this.grid);
        Stack<Point2D> tileStack = this.company.getTileStack();
        assertEquals(tileStack.size(), 1);
        this.actions.tiles(-1 , "Sell", this.company, this.grid);
        assertEquals(tileStack.size(), 0);

    }
}