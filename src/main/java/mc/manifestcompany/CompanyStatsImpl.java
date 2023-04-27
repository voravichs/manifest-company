package mc.manifestcompany;
import java.util.Stack;

public class CompanyStatsImpl implements CompanyStats {
    private int price;
    private double multiplier;
    private int capacity;
    private int cost;
    private int cash;
    private int numTiles;

    private Stack<Integer> revenue;
    private Stack<Integer> cogs;
    private Stack<Integer> profit;


    public CompanyStatsImpl() {
        this.price = 50;
        this.multiplier = 1.0;
        this.capacity = 15;
        this.cost = 30;
        this.cash = 500;
        this.numTiles = 1;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(int amount) {
        this.price = amount;
    }

    @Override
    public double getMultiplier() {
        return this.multiplier;
    }

    @Override
    public void setMultiplier(double amount) {
        this.multiplier = amount;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void setCapacity(int amount) {
        this.capacity = amount;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public void setCost(int amount) {
        this.cost = amount;
    }

    @Override
    public int getCash() {
        return this.cash;
    }

    @Override
    public void setCash(int amount) {
        this.cash = amount;
    }

    @Override
    public void setRevenue(int amount) {
        revenue.push(amount);
    }

    @Override
    public int getRevenue() {
        return revenue.pop();
    }

    @Override
    public void setCogs(int amount) {
        cogs.push(amount);
    }

    @Override
    public int getCogs() {
        return cogs.pop();
    }

    @Override
    public void setProfit(int amount) {
        profit.push(amount);
    }

    @Override
    public int getProfit() {
        return profit.pop();
    }

    @Override
    public int getNumTiles() {
        return this.numTiles;
    }

    @Override
    public void setNumTiles(int amount) {
        this.numTiles = amount;
    }
}
