package mc.manifestcompany.company;

import mc.manifestcompany.DataType;
import mc.manifestcompany.gui.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import static org.junit.Assert.*;

public class UserCompanyTest {
    private Company company;

    private Tile[][] grid;
    @Before
    public void setUp() throws Exception {
        this.company = new UserCompany("company", new CompanyActionImpl(),
                Tile.TileType.CLAIMED_P1, "images/playerfastfood.png");
    }

    @Test
    public void initializeStats() {
        HashMap<Enum<DataType>, Integer> stats = this.company.getStats();
        int price = stats.get(DataType.PRICE);
        assertEquals(50, price);
        int multiplier = stats.get(DataType.MULTIPLIER);
        assertEquals(0, multiplier);
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
        assertEquals(1, multiplier);
        assertEquals(400, cash);

        this.company.invest(100, "R&D");
        int price = this.company.getStats().get(DataType.PRICE);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(60, price);
        assertEquals(300, cash);


        this.company.invest(100, "Goods");
        int capacity = this.company.getStats().get(DataType.CAPACITY);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(16, capacity);
        assertEquals(200, cash);

        this.company.invest(100, "HR");
        int cost = this.company.getStats().get(DataType.COST);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(27, cost);
        assertEquals(100, cash);
    }

    @Test
    public void tiles() {
        this.company.tiles(1, "Purchase", grid);
        int tiles = this.company.getStats().get(DataType.TILES);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(2, tiles);
        assertEquals(200, cash);

        this.company.tiles(1, "Sell", grid);
        tiles = this.company.getStats().get(DataType.TILES);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(1, tiles);
        assertEquals(300, cash);
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



//    @Test
//    public void getStats() {
//    }
//
//    @Test
//    public void setStats() {
//    }

//    @Test
//    public void setRevenue() {
//    }
//
//    @Test
//    public void setCogs() {
//    }
//
//    @Test
//    public void setProfit() {
//    }

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
}