package mc.manifestcompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NPCAction extends CompanyActionImpl {
    private HashMap<Enum<DataType>, Integer> NPCstats;
    private Random random;

    private static final int TILE_COST = 300;
    private static final int TILE_VALUE = 100;

    public NPCAction() {
        random = new Random();
    }

    private int getRandomAmount(NPCCompany company) {
        int availableCash = company.getStats().get(DataType.CASH);
        if (availableCash <= 0) {
            return 0;
        }
        return random.nextInt(availableCash) + 1;
    }

    private String getRandomSector() {
        String[] sectors = {"Marketing", "R&D", "Goods", "HR"};
        return sectors[random.nextInt(sectors.length)];
    }

    private String getRandomMethod() {
        String[] methods = {"Purchase", "Sell"};
        return methods[random.nextInt(methods.length)];
    }

    private int getRandomTileCount(NPCCompany company) {
        int availableCash = company.getStats().get(DataType.CASH);
        int maxTiles = availableCash / TILE_COST;
        if (maxTiles <= 0) {
            return 0;
        }
        return random.nextInt(maxTiles) + 1;
    }

    public void performRandomAction(NPCCompany company) {
        int availableCash = company.getStats().get(DataType.CASH);
        boolean canInvest = availableCash > 0;
        boolean canBuyTiles = availableCash >= TILE_COST;
        int availableTiles = company.getStats().get(DataType.TILES);

        if (canInvest || canBuyTiles) {
            List<Integer> availableActions = new ArrayList<>();
            if (canInvest) {
                availableActions.add(0);
            }
            if (canBuyTiles) {
                availableActions.add(1);
            }

            int actionChoice = availableActions.get(random.nextInt(availableActions.size()));

            if (actionChoice == 0) {
                invest(0, "", company);
            } else if (actionChoice == 1) {
                tiles(0, "Purchase", company);
            }
        } else if (availableTiles > 0) {
            tiles(0, "Sell", company);
        }
    }

    //@Override
    public void invest(int amount, String sector, NPCCompany company) {
        int randomAmount = getRandomAmount(company);
        String randomSector = getRandomSector();

        System.out.println("NPC DECISION: INVESTING: " + randomAmount +
                ", in sector: " + randomSector +
                ", of company: " + company.getName());
        this.NPCstats = company.getStats();

        boolean success = switch (randomSector) {
            case "Marketing" -> investMarketing(randomAmount);
            case "R&D" -> investRD(randomAmount);
            case "Goods" -> investGoods(randomAmount);
            case "HR" -> investHumanCapital(randomAmount);
            default -> false;
        };

        if (!success) {
            System.out.println(" Failed to invest" + randomAmount +
                    " in sector: " + randomSector +
                    " of company: " + company.getName());
        } else {
            System.out.println("Success!" + "\n");
        }
        company.setStats(this.NPCstats);
    }

    //@Override
    public void tiles(int numTile, String method, NPCCompany company) {
        int randomTileCount = getRandomTileCount(company);

        System.out.println("NPC DECISION: " + method + " " + randomTileCount +
                " tiles for company: " + company.getName());
        this.NPCstats = company.getStats();

        boolean success = switch (method) {
            case "Purchase" -> purchaseTiles(numTile);
            case "Sell" -> sellTiles(numTile);
            default -> false;
        };
        if (!success) {
            System.out.println(" Failed to " + method + " " + numTile +
                    "tiles for company: " + company.getName());
        } else {
            System.out.println("Success!" + "\n");
        }
        company.setStats(this.NPCstats);
    }

    /**
     * Handles changes to cash on hand
     * @param amount increase or decrease in cash based on whether amount passed in is + or -
     * @return whether the action was successful
     */
    private boolean handleCash(int amount) {
        int cash = this.NPCstats.get(DataType.CASH);
        if(amount < 0 && cash < -amount) {
            return false;
        }
        cash += amount;
        this.NPCstats.put(DataType.CASH, cash);
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
        int multiplier = this.NPCstats.get(DataType.MULTIPLIER);
        this.NPCstats.put(DataType.MULTIPLIER, multiplier + increase);
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
        int price = this.NPCstats.get(DataType.PRICE);
        this.NPCstats.put(DataType.PRICE, price + (increase * 10));
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
        int capacity = this.NPCstats.get(DataType.CAPACITY);
        this.NPCstats.put(DataType.CAPACITY, capacity + increase);
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
        int cost = this.NPCstats.get(DataType.COST);
        this.NPCstats.put(DataType.COST, cost - (decrease * 3));
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
        this.NPCstats.put(DataType.TILES, this.NPCstats.get(DataType.TILES) + numTiles);
        // TODO: TILE HANDLING - BFS to add a new tile
        return true;
    }

    /**
     * handles selling of tiles
     * @param numTiles number of tiles to sell - each tile is worth $100
     * @return whether action was successful
     */
    private boolean sellTiles(int numTiles) {
        if (this.NPCstats.get(DataType.TILES) < numTiles) {
            return false;
        }
        int profit = numTiles * TILE_VALUE;
        handleCash(profit);
        this.NPCstats.put(DataType.TILES, this.NPCstats.get(DataType.TILES) - numTiles);
        // TODO: TILE HANDLING - remove most recent tile
        return true;
    }

}
