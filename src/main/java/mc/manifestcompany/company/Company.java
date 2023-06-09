package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gui.Tile;

import java.util.*;

/**
 * Defines an abstract company.
 * Subclasses include a User and NPC company.
 * @author Team Manifest Company
 */
public abstract class Company {

    public abstract void initializeStats();

    /**
     * Calls invest method in CompanyAction.
     * @param num amount to invest
     * @param sector sector to invest in
     */
    public abstract void invest(int num, String sector);


    /**
     * Calls tiles method in CompanyAction
     * @param numTiles number of tiles to buy or sell
     * @param method "Purchase" or "Sell" tiles
     */
    public abstract void tiles(int numTiles, String method, Tile[][] grid);


    /**
     * Sets the company name.
     * @param name company name to set
     */
    public abstract void setName(String name);

    /**
     * Gets the company name.
     * @return the company name
     */
    public abstract String getName();

    /**
     * Gets the company's stats object
     * @return the company stats object
     */
    public abstract EnumMap<DataType, Integer> getStats();

    /**
     * Sets the company's stats
     * @param stats company stats to set
     */
    public abstract void setStats(EnumMap<DataType, Integer> stats);

    /**
     * push the newest revenue to revenue stack
     * @param amount revenue amount
     */
    public abstract void setRevenue(int amount);

    /**
     * push the newest cogs to cogs stack
     * @param amount cogs amount
     */
    public abstract void setCogs(int amount);

    /**
     * push the newest profit to profit stack
     * @param amount profit amount
     */
    public abstract void setProfit(int amount);

    /**
     * gets the image link of this company
     * @return String image link
     */
    public abstract String getImageLink();

    /**
     * return a list of current financial data in the order:
     * revenue, cogs, profit
     */
    public abstract List<Integer> getFinancials();

    /**
     * return a list of stack that contain all the past finanical data
     * in the order of: revenue, cogs, profit
     * @return
     */
    public abstract List<Stack<Integer>> getFinancialHistory();

    public abstract Tile.TileType getTileType();

    public static Comparator<Company> comparatorBy(DataType dataType) {
        return new Comparator<Company>() {
            @Override
            public int compare(Company o1, Company o2) {
                return o2.getStats().get(dataType) - o1.getStats().get(dataType);
            }
        };
    }

    public abstract Stack<Point2D> getTileStack();

    public abstract void addToStack(Point2D newTile);

    public abstract Point2D popFromStack();

    //update tiles value




}
