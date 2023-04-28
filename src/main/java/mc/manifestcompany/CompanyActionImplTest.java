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
        this.company.tiles(1, "Purchase");
        int tiles = this.company.getStats().get(DataType.TILES);
        int cash = this.company.getStats().get(DataType.CASH);
        assertEquals(2, tiles);
        assertEquals(200, cash);

        this.company.tiles(1, "Sell");
        tiles = this.company.getStats().get(DataType.TILES);
        cash = this.company.getStats().get(DataType.CASH);
        assertEquals(1, tiles);
        assertEquals(300, cash);

    }
}