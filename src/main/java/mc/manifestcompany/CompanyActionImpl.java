package mc.manifestcompany;

/**
 * Implementation of the company actions interface.
 * @author Team Manifest Company
 */
public class CompanyActionImpl implements CompanyAction {

    private Company company;
    private CompanyStats stats;

    private static final int TILE_COST = 300;
    private static final int TILE_VALUE = 100;

    @Override
    public void invest(int amount, String sector, Company company) {
        System.out.println("INVESTING: " + amount +
                ", in sector: " + sector +
                ", of company: " + company.getName());
        this.company = company;
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
            company.setStats(stats);
        }
    }

    @Override
    public void tiles(int numTile, String method, Company company) {
        this.company = company;
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
            company.setStats(stats);
        }
    }
    @Override
    public boolean handleCash(int amount) {
        int cash = stats.getCash();
        if(amount < 0 && cash < -amount) {
            return false;
        }
        cash += amount;
        stats.setCash(cash);
        return true;
    }

    @Override
    public boolean investMarketing(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        double multiplier = stats.getMultiplier();
        stats.setMultiplier(multiplier + (increase * 0.1));
        return true;
    }

    @Override
    public boolean investRD(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int price = stats.getPrice();
        stats.setPrice(price + (increase * 10));
        return true;
    }

    @Override
    public boolean investGoods(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int increase = amount / 100;
        int capacity = stats.getCapacity();
        stats.setCapacity(capacity + increase);
        return true;
    }

    @Override
    public boolean investHumanCapital(int amount) {
        if(!handleCash(-amount)) {
            return false;
        }
        int decrease = amount / 100;
        int cost = stats.getCost();
        stats.setCost(cost - (decrease * 3));
        return true;
    }

    @Override
    public boolean purchaseTiles(int numTiles) {
        int cost = numTiles * TILE_COST;
        if (stats.getCash() < cost) {
            return false;
        }
        stats.setCash(stats.getCash() - cost);
        stats.setNumTiles(stats.getNumTiles() + numTiles);
        // TODO: TILE HANDLING - BFS to add a new tile
        return true;
    }

    @Override
    public boolean sellTiles(int numTiles) {
        if (stats.getNumTiles() < numTiles) {
            return false;
        }
        int profit = numTiles * TILE_VALUE;
        stats.setCash(stats.getCash() + profit);
        stats.setNumTiles(stats.getNumTiles() - numTiles);
        // TODO: TILE HANDLING - remove most recent num
        return true;
    }



}
