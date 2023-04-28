package mc.manifestcompany;

import java.util.HashMap;

/**
 * Implementation of the company actions interface.
 * @author Team Manifest Company
 */
public class CompanyActionImpl implements CompanyAction {

    private HashMap<Enum<DataType>, Integer> stats;


    private static final int TILE_COST = 300;
    private static final int TILE_VALUE = 100;

    @Override
    public void invest(int amount, String sector, Company company) {
        System.out.println("INVESTING: " + amount +
                ", in sector: " + sector +
                ", of company: " + company.getName());
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
                "tiles for company: " + company.getName());
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
    @Override
    public boolean handleCash(int amount) {
        int cash = this.stats.get(DataType.CASH);
        if(amount < 0 && cash < -amount) {
            return false;
        }
        cash += amount;
        this.stats.put(DataType.CASH, cash);
        return true;
    }

    @Override
    public boolean investMarketing(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int multiplier = this.stats.get(DataType.MULTIPLIER);
        this.stats.put(DataType.MULTIPLIER, multiplier + increase);
        return true;
    }

    @Override
    public boolean investRD(int amount) {

        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int price = this.stats.get(DataType.PRICE);
        this.stats.put(DataType.PRICE, price + (increase * 10));
        return true;
    }

    @Override
    public boolean investGoods(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int capacity = this.stats.get(DataType.CAPACITY);
        this.stats.put(DataType.MULTIPLIER, capacity + increase);
        return true;
    }

    @Override
    public boolean investHumanCapital(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int decrease = amount / 100;
        int cost = this.stats.get(DataType.COST);
        this.stats.put(DataType.COST, cost - (decrease * 3));
        return true;
    }

    @Override
    public boolean purchaseTiles(int numTiles) {
        int cost = numTiles * TILE_COST;
        if(!handleCash(-cost)) {
            return false;
        }
        this.stats.put(DataType.TILES, this.stats.get(DataType.TILES) + numTiles);
        // TODO: TILE HANDLING - BFS to add a new tile
        return true;
    }

    @Override
    public boolean sellTiles(int numTiles) {
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
