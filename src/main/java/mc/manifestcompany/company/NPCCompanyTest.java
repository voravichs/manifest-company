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

public class NPCCompanyTest {
    private NPCCompany npcCompany;
    private NPCActionImpl npcActions;
    private Tile[][] grid;

    @Before
    public void setUp() {
        this.npcCompany = new NPCCompany("NPC Company", new NPCActionImpl(),
                Tile.TileType.CLAIMED_P2, "images/npcfastfood.png");

        this.grid = new Tile[20][20];

        this.npcActions = new NPCActionImpl();

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
        EnumMap<DataType, Integer> stats = this.npcCompany.getStats();
        int price = stats.get(DataType.PRICE);
        assertEquals(50, price);
        int multiplier = stats.get(DataType.MULTIPLIER);
        assertEquals(1, multiplier);
        int capacity = stats.get(DataType.CAPACITY);
        assertEquals(15, capacity);
        int cost = stats.get(DataType.COST);
        assertEquals(30, cost);
        int cash = stats.get(DataType.CASH);
        assertEquals(500, cash);
        int tiles = stats.get(DataType.TILES);
        assertEquals(1, tiles);
    }

    @Test
    public void invest() {
        this.npcCompany.invest(100, "Marketing");
        // Add appropriate assertions based on the invest method implementation in NPCActionImpl
    }

    @Test
    public void tiles() {
        this.npcCompany.tiles(1, "Purchase", grid);
        int tiles = this.npcCompany.getStats().get(DataType.TILES);
        int cash = this.npcCompany.getStats().get(DataType.CASH);
        // Add appropriate assertions based on the tiles method implementation in NPCActionImpl
    }

    @Test
    public void getName() {
        this.npcCompany.setName("NPC Company");
        assertEquals("NPC Company", this.npcCompany.getName());
    }

    @Test
    public void setName() {
        this.npcCompany.setName("New NPC Company");
        assertEquals("New NPC Company", this.npcCompany.getName());
    }

    @Test
    public void getStats() {
        EnumMap<DataType, Integer> stats = this.npcCompany.getStats();
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
        // Add a test case to set stats and verify if the updated stats are correct
    }

    @Test
    public void setRevenue() {
        // Add a test case to set revenue and verify if the updated revenue is correct
    }

    @Test
    public void setCogs() {
        // Add a test case to set cogs and verify if the updated cogs are correct
    }

    @Test
    public void setProfit() {
        // Add a test case to set profit and verify if the updated profit is correct
    }

    @Test
    public void getFinancials() {
        this.npcCompany.setRevenue(100);
        this.npcCompany.setCogs(10);
        this.npcCompany.setProfit(30);
        assertEquals(Arrays.asList(100, 10, 30), this.npcCompany.getFinancials());
    }

    @Test
    public void getFinancialHistory() {
        this.npcCompany.setRevenue(100);
        this.npcCompany.setRevenue(300);
        this.npcCompany.setCogs(10);
        this.npcCompany.setCogs(30);
        this.npcCompany.setProfit(30);
        this.npcCompany.setProfit(60);
        Stack<Integer> rev = new Stack<>();
        rev.add(100);
        rev.add(300);
        Stack<Integer> cogs = new Stack<>();
        cogs.add(10);
        cogs.add(30);
        Stack<Integer> profit = new Stack<>();
        profit.add(30);
        profit.add(60);
        assertEquals(Arrays.asList(rev, cogs, profit), this.npcCompany.getFinancialHistory());
    }

    @Test
    public void getTileStack() {
        this.npcActions.tiles(1 , "Purchase", this.npcCompany, this.grid);
        Stack<Point2D> tileStack = this.npcCompany.getTileStack();
        assertEquals(tileStack.size(), 1);
    }

    @Test
    public void addToStack() {
        this.npcActions.tiles(1 , "Purchase", this.npcCompany, this.grid);
        Stack<Point2D> tileStack = this.npcCompany.getTileStack();
        assertEquals(tileStack.size(), 1);
    }

    @Test
    public void purchaseTile() {
        this.npcActions.tiles(1 , "Purchase", this.npcCompany, this.grid);
        Stack<Point2D> tileStack = this.npcCompany.getTileStack();
        assertEquals(1, tileStack.size());
    }

    @Test
    public void sellTile() {
        // Purchase a tile first
        this.npcActions.tiles(1 , "Purchase", this.npcCompany, this.grid);
        Stack<Point2D> tileStack = this.npcCompany.getTileStack();
        assertEquals(1, tileStack.size());

        // Sell the tile
        this.npcActions.tiles(-1 , "Sell", this.npcCompany, this.grid);
        assertEquals(1, tileStack.size());
    }

}
