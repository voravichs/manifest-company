package mc.manifestcompany;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

public class UserCompanyTest {
    private Company company;
    @Before
    public void setUp() throws Exception {
        this.company = new UserCompany("company", new CompanyActionImpl());
    }

    @Test
    public void initializeStats() {
        HashMap<Enum<DataType>, Integer> stats = this.company.getStats();
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
    }

    @Test
    public void tiles() {
    }


//    @Test
//    public void getName() {
//        this.company.setName("player");
//        assertEquals("player", this.company.getName());
//    }
//
//    @Test
//    public void setName() {
//        this.company.setName("company");
//        assertEquals("company", this.company.getName());
//    }



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