package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gui.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Stack;

import static org.junit.Assert.*;

public class UserCompanyTest {
    private Company company;

    private CompanyAction actions;

    private Tile[][] grid;
    @Before
    public void setUp() {
        this.company = new UserCompany("company", new CompanyActionImpl(),
                Tile.TileType.CLAIMED_P1, "images/playerfastfood.png");

        this.grid = new Tile[20][20];

        this.actions = new CompanyActionImpl();

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
    public void initializeStats() {
        EnumMap<DataType, Integer> stats = this.company.getStats();
        int price = stats.get(DataType.PRICE);
        assertEquals(50, price);
        int multiplier = stats.get(DataType.MULTIPLIER);
        assertEquals(1, multiplier);
        int capacity = stats.get(DataType.CAPACITY);
        assertEquals(15, capacity);
        int cost = stats.get(DataType.COST);
        assertEquals(30, cost);
    }

    @Test
    public void invest() {
        this.company.invest(100, "Marketing");
        int multiplier = this.company.getStats().get(DataType.MULTIPLIER);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(3, multiplier);
        assertEquals(400, cash);

        this.company.invest(100, "R&D");
        int price = this.company.getStats().get(DataType.PRICE);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(70, price);
        assertEquals(300, cash);


        this.company.invest(100, "Goods");
        int capacity = this.company.getStats().get(DataType.CAPACITY);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(17, capacity);
        assertEquals(200, cash);

        this.company.invest(100, "HR");
        int cost = this.company.getStats().get(DataType.COST);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(24, cost);
        assertEquals(100, cash);
    }

    @Test
    public void tiles() {
        this.company.tiles(1, "Purchase", grid);
        int tiles = this.company.getStats().get(DataType.TILES);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(2, tiles);
        assertEquals(200, cash);

        this.company.tiles(-1, "Sell", grid);
        tiles = this.company.getStats().get(DataType.TILES);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(1, tiles);
        assertEquals(500, cash);
    }


    @Test
    public void getName() {
        this.company.setName("player");
        assertEquals("player", this.company.getName());
    }

    @Test
    public void setName() {
        this.company.setName("company");
        assertEquals("company", this.company.getName());
    }



    @Test
    public void getStats() {
        EnumMap<DataType, Integer> stats = this.company.getStats();
        int price = stats.get(DataType.PRICE);
        assertEquals(50, price);
        int multiplier = stats.get(DataType.MULTIPLIER);
        assertEquals(1, multiplier);
        int capacity = stats.get(DataType.CAPACITY);
        assertEquals(15, capacity);
        int cost = stats.get(DataType.COST);
        assertEquals(30, cost);

    }

    @Test
    public void setStats() {
    }

    @Test
    public void setRevenue() {
    }

    @Test
    public void setCogs() {
    }

    @Test
    public void setProfit() {
    }

    @Test
    public void getFinancials() {
        this.company.setRevenue(100);
        this.company.setCogs(10);
        this.company.setProfit(30);
        assertEquals(Arrays.asList(100, 10, 30), this.company.getFinancials());

    }

    @Test
    public void getFinancialHistory() {
        this.company.setRevenue(100);
        this.company.setRevenue(300);
        this.company.setCogs(10);
        this.company.setCogs(30);
        this.company.setProfit(30);
        this.company.setProfit(60);
        Stack<Integer> rev = new Stack<>();
        rev.add(100);
        rev.add(300);
        Stack<Integer> cogs = new Stack<>();
        cogs.add(10);
        cogs.add(30);
        Stack<Integer> profit = new Stack<>();
        profit.add(30);
        profit.add(60);
        assertEquals(Arrays.asList(rev, cogs, profit), this.company.getFinancialHistory());
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