package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gui.Tile;

import java.util.*;

/**
 * Subclass of Player that defines a Playable Company of the user.
 * @author Team Manifest Company
 */
public class UserCompany extends Company {
    private String name;
    private CompanyAction actions;
    private String imageLink;
    private Stack<Integer> revenue;
    private Stack<Integer> cogs;
    private Stack<Integer> profit;
    private EnumMap<DataType, Integer> stats;
    private Stack<Point2D> tileStack;
    private List<DataType> lastInvestments;

    private Tile.TileType tileType;

    /**
     * Constructor for the UserCompany class.
     * @param name The name of the user company.
     * @param actions The CompanyAction object associated with the user company.
     * @param tileType The type of tile associated with the user company.
     * @param imageLink The image link for the user company.
     */
    public UserCompany(String name, CompanyAction actions, Tile.TileType tileType,
                       String imageLink) {
        this.name = name;
        this.actions = actions;
        this.imageLink = imageLink;
        this.stats = new EnumMap<>(DataType.class);
        this.revenue = new Stack<>();
        this.cogs = new Stack <>();
        this.profit = new Stack <>();
        this.tileStack = new Stack <>();
        this.tileType = tileType;
        this.lastInvestments = new ArrayList<>();
        initializeStats();
    }

    /**
     * Initializes the statistics for the user company.
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
     * Invests the specified amount in the given sector for the user company.
     * @param amount The amount to invest.
     * @param sector The sector to invest in.
     */
    @Override
    public void invest(int amount, String sector) {
        System.out.println("Investing in " + this.name);
        actions.invest(amount, sector, this);
    }

    /**
     * Purchases or sells tiles for the user company based on the provided method.
     * @param num The number of tiles to purchase or sell.
     * @param method The method to use, either "Purchase" or "Sell".
     * @param grid The grid of tiles.
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
     * Returns the financials of the user company as a list of integers.
     * @return A list containing revenue, cogs, and profit.
     */
    @Override
    public List<Integer> getFinancials() {
        return Arrays.asList(this.revenue.peek(), this.cogs.peek(), this.profit.peek());
    }

    /**
     * Returns the financial history of the user company as a list of stacks of integers.
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

    /**
     * Checks whether an investment is valid.
     * Compares the amount of cash on hand and the current amount of tiles.
     * @param amount increase or decrease in cash based on whether amount passed in is + or -
     * @param tiles amount of tiles to SELL (+ sell, - buy)
     * @return whether the action was successful
     */
    public boolean checkValidInvest(int amount, int tiles) {
        // Have enough cash to cover all expenses,
        // always have enough when selling (+ amount)
        boolean enoughCash = amount >= 0 || this.stats.get(DataType.CASH) >= -amount;
        // Can only sell until you have 1 tile left,
        // can always buy (- tiles) if you have money (checked above)
        boolean enoughTiles = -tiles >= 0 || this.stats.get(DataType.TILES) - tiles >= 1;
        return enoughTiles && enoughCash;
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
