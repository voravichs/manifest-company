package mc.manifestcompany;

import java.util.HashMap;

/**
 * Implementation of the company actions interface.
 * @author Team Manifest Company
 */
public class CompanyActionImpl implements CompanyAction {

    protected HashMap<Enum<DataType>, Integer> stats;
    protected static final int TILE_COST = 300;
    protected static final int TILE_VALUE = 100;

    @Override
    public void invest(int amount, String sector, Company company) {
        System.out.println("INVESTING: " + amount +
                " in sector: " + sector +
                " of company: " + company.getName());
        this.stats = company.getStats();
        boolean success = switch (sector) {
            case "Marketing" -> investMarketing(amount);
            case "R&D" -> investRD(amount);
            case "Goods" -> investGoods(amount);
            case "HR" -> investHumanCapital(amount);
            default -> false;
        };
        if (!success) {
            System.out.println(" Failed to invest" + amount +
                    " in sector: " + sector +
                    " of company: " + company.getName());
        } else {
            System.out.println("Success!");
        }
        company.setStats(this.stats);
    }

    @Override
    public void tiles(int numTile, String method, Company company) {
        this.stats = company.getStats();
        System.out.println(method + " " + numTile +
                " tiles for company: " + company.getName());
        boolean success = switch (method) {
            case "Purchase" -> purchaseTiles(numTile);
            case "Sell" -> sellTiles(numTile);
            default -> false;
        };
        if (!success) {
            System.out.println(" Failed to " + method + " " + numTile +
                    "tiles for company: " + company.getName());
        } else {
            System.out.println("Success!");
        }
        company.setStats(stats);
    }



    /**
     * Handles changes to cash on hand
     * @param amount increase or decrease in cash based on whether amount passed in is + or -
     * @return whether the action was successful
     */
    private boolean handleCash(int amount) {
        int cash = this.stats.get(DataType.CASH);
        if(amount < 0 && cash < -amount) {
            return false;
        }
        cash += amount;
        this.stats.put(DataType.CASH, cash);
        return true;
    }

    /**
     * handles marketing investment
     * @param amount amount to invest - each $100 leads to a 10% multiplier increase
     * @return whether the action was successful
     */
    private boolean investMarketing(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int multiplier = this.stats.get(DataType.MULTIPLIER);
        this.stats.put(DataType.MULTIPLIER, multiplier + increase);
        return true;
    }
    /**
     * handles R&D investment
     * @param amount amount to invest - each $100 leads to a $10 price increase
     * @return whether the action was successful
     */
    private boolean investRD(int amount) {

        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int price = this.stats.get(DataType.PRICE);
        this.stats.put(DataType.PRICE, price + (increase * 10));
        return true;
    }

    /**
     * handles Raw Goods investment
     * @param amount amount to invest - each $100 leads to 1 unit capacity increase
     * @return whether the action was successful
     */
    private boolean investGoods(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int capacity = this.stats.get(DataType.CAPACITY);
        this.stats.put(DataType.CAPACITY, capacity + increase);
        return true;
    }
    /**
     * handles Human Capital investment
     * @param amount amount to invest - each $100 leads to $3 cost decrease
     * @return whether the action was successful
     */
    private boolean investHumanCapital(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int decrease = amount / 100;
        int cost = this.stats.get(DataType.COST);
        this.stats.put(DataType.COST, cost - (decrease * 3));
        return true;
    }

    /**
     * handles purchasing of tiles
     * @param numTiles number of tiles to purchase - each tile is $300
     * @return whether action was successful
     */
    private boolean purchaseTiles(int numTiles) {
        int cost = numTiles * TILE_COST;
        if(!handleCash(-cost)) {
            return false;
        }
        this.stats.put(DataType.TILES, this.stats.get(DataType.TILES) + numTiles);
        // TODO: TILE HANDLING - BFS to add a new tile
        return true;
    }

    /**
     * handles selling of tiles
     * @param numTiles number of tiles to sell - each tile is worth $100
     * @return whether action was successful
     */
    private boolean sellTiles(int numTiles) {
        if (this.stats.get(DataType.TILES) < numTiles) {
            return false;
        }
        int profit = numTiles * TILE_VALUE;
        handleCash(profit);
        this.stats.put(DataType.TILES, this.stats.get(DataType.TILES) - numTiles);
        // TODO: TILE HANDLING - remove most recent tile
        return true;
    }



}
