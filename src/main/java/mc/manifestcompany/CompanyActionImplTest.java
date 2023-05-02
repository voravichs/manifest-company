package mc.manifestcompany;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanyActionImplTest {

    private Company company;

    private CompanyAction actions;
    @Before
    public void setUp() throws Exception {
        this.actions = new CompanyActionImpl();
        this.company = new UserCompany("company", this.actions);

    }

    @Test
    public void invest() {
        this.actions.invest(100, "Marketing", this.company);
        int multiplier = this.company.getStats().get(DataType.MULTIPLIER);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(1, multiplier);
        assertEquals(400, cash);

        this.actions.invest(100, "R&D", this.company);
        int price = this.company.getStats().get(DataType.PRICE);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(60, price);
        assertEquals(300, cash);


        this.actions.invest(100, "Goods", this.company);
        int capacity = this.company.getStats().get(DataType.CAPACITY);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(16, capacity);
        assertEquals(200, cash);

        this.actions.invest(100, "HR", this.company);
        int cost = this.company.getStats().get(DataType.COST);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(27, cost);
        assertEquals(100, cash);
    }

    @Test
    public void tiles() {
        this.actions.tiles(1, "Purchase", this.company);
        int tiles = this.company.getStats().get(DataType.TILES);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(2, tiles);
        assertEquals(200, cash);

        this.actions.tiles(1, "Sell", this.company);
        tiles = this.company.getStats().get(DataType.TILES);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(1, tiles);
        assertEquals(300, cash);

    }
}