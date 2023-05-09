package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gui.Tile;

import java.util.*;

/**
 * Subclass of Player that defines a Non-Playable Company (NPC).
 * @author Team Manifest Company
 */
public class NPCCompany extends Company {
    private String name;
    private NPCActionImpl actions;
    private String imageLink;
    private Stack<Integer> revenue;
    private Stack<Integer> cogs;
    private Stack<Integer> profit;
    private EnumMap<DataType, Integer> stats;
    private Tile.TileType tileType;
    private DataType lastInvestment;

    private Stack<Point2D> tileStack;

    /**
     * Constructor for the NPCCompany class.
     * @param name The name of the NPC company.
     * @param actions The NPCActionImpl object containing the company's actions.
     * @param tileType The TileType of the company's tiles.
     * @param imageLink A string representing the link to the company's image.
     */
    public NPCCompany(String name, NPCActionImpl actions, Tile.TileType tileType, String imageLink) {
        this.name = name;
        this.actions = actions;
        this.imageLink = imageLink;
        this.stats = new EnumMap<>(DataType.class);
        this.revenue = new Stack<>();
        this.cogs = new Stack <>();
        this.profit = new Stack <>();
        this.tileStack = new Stack <>();
        this.tileType = tileType;
        initializeStats();
    }

    /**
     * Initializes the statistics for the NPC companies.
     */
    @Override
    public void initializeStats() {
        this.stats.put(DataType.PRICE, 50);
        this.stats.put(DataType.MULTIPLIER, 1);
        this.stats.put(DataType.CAPACITY, 15);
        this.stats.put(DataType.COST, 30);
        this.stats.put(DataType.CASH, 500);
        this.stats.put(DataType.TILES, 1);
    }

    /**
     * Invests the given amount in the specified sector for this NPC company.
     * @param num The amount to be invested.
     * @param sector The sector in which the amount will be invested.
     */
    @Override
    public void invest(int num, String sector) {
        System.out.println("Investing in " + this.name);
        actions.invest(num, sector, this);
    }

    /**
     * Purchases or sells tiles for this NPC company based on the provided method.
     * @param num The number of tiles to purchase or sell.
     * @param method The method to be used for this action, either "Purchase" or "Sell".
     * @param grid A 2D array of Tile objects representing the game grid.
     */
    @Override
    public void tiles(int num, String method, Tile[][] grid) {
        System.out.println("Purchasing/Selling tiles in " + this.name);
        actions.tiles(num, method, this, grid);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public NPCActionImpl getActions() {
        return this.actions;
    }

    @Override
    public EnumMap<DataType, Integer> getStats() {
        return this.stats;
    }

    @Override
    public void setStats(EnumMap<DataType, Integer> stats) {
        this.stats = stats;
    }

    @Override
    public void setRevenue(int amount) {
        revenue.push(amount);
    }

    @Override
    public void setCogs(int amount) {
        cogs.push(amount);
    }

    @Override
    public void setProfit(int amount) {
        profit.push(amount);
    }

    @Override
    public String getImageLink() {
        return this.imageLink;
    }

    /**
     * Returns the financials of the NPC company as a list of integers.
     * @return A list containing revenue, cogs, and profit.
     */
    @Override
    public List<Integer> getFinancials() {
        return Arrays.asList(this.revenue.peek(), this.cogs.peek(), this.profit.peek());
    }

    /**
     * Returns the financial history of the NPC company as a list of stacks of integers.
     * @return A list containing stacks of revenue, cogs, and profit.
     */
    @Override
    public List<Stack<Integer>> getFinancialHistory() {
        return Arrays.asList(revenue, cogs, profit);
    }

    @Override
    public Tile.TileType getTileType() {
        return tileType;
    }

    @Override
    public Stack<Point2D> getTileStack() {
        return this.tileStack;
    }

    @Override
    public void addToStack(Point2D newTile) {
        this.tileStack.push(newTile);
    }
    @Override
    public Point2D popFromStack() {
        return this.tileStack.pop();
    }
}
