package mc.manifestcompany;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Subclass of Player that defines a Non-Playable Company (NPC).
 * TODO: ADD METHODS THAT DIFFERENTIATE THIS FROM USER
 * @author Team Manifest Company
 */
public class NPCompany extends Company {
    private String name;
    private CompanyAction actions;
    private Stack<Integer> revenue;
    private Stack<Integer> cogs;
    private Stack<Integer> profit;
    private HashMap<Enum<DataType>, Integer> stats;

    public NPCompany(String name, CompanyAction actions) {
        this.name = name;
        this.actions = actions;
        this.stats = new HashMap<>();
        this.revenue = new Stack<>();
        this.cogs = new Stack <>();
        this.profit = new Stack <>();
        initializeStats();
    }

    @Override
    public void initializeStats() {
        this.stats.put(DataType.PRICE, 50);
        this.stats.put(DataType.MULTIPLIER, 1);
        this.stats.put(DataType.CAPACITY, 15);
        this.stats.put(DataType.COST, 30);
        this.stats.put(DataType.CASH, 500);
        this.stats.put(DataType.TILES, 1);
    }

    @Override
    public void invest(int num, String sector) {
        System.out.println("Investing in " + this.name);
        actions.invest(num, sector, this);
    }

    @Override
    public void tiles(int num, String method) {
        System.out.println("Purchasing/Selling tiles in " + this.name);
        actions.tiles(num, method, this);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public HashMap<Enum<DataType>, Integer> getStats() {
        return this.stats;
    }

    @Override
    public void setStats(HashMap<Enum<DataType>, Integer> stats) {
        this.stats = stats;
    }
    @Override
    public void setRevenue(int amount) { revenue.push(amount); }
    @Override
    public void setCogs(int amount) { cogs.push(amount); }

    @Override
    public void setProfit(int amount) { profit.push(amount); }

    @Override
    public List getFinancials() {
        return Arrays.asList(this.revenue.peek(), this.cogs.peek(), this.profit.peek());
    }
    @Override
    public List<Stack> getFinancialHistory() {
        return Arrays.asList(revenue, cogs, profit);
    }
}
