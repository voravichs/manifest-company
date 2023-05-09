package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gamelogic.Event;
import mc.manifestcompany.gamelogic.Game;
import mc.manifestcompany.gui.Tile;

import java.util.*;

public class NPCActionImpl extends CompanyActionImpl {
    private Random random;
    private DataType lastInvestment;
    public NPCActionImpl() {
        random = new Random();
    }

    private int getRandomAmount(NPCCompany company) {
        int availableCash = company.getStats().get(DataType.CASH);
        if (availableCash <= 0) {
            return 0;
        }

        if (availableCash < 100) {
            return availableCash;
        }

        int randomAmount = random.nextInt(availableCash / 100) + 1;
        return randomAmount * 100;
    }

    private String getRandomSector() {
        String[] sectors = {"Marketing", "R&D", "Goods", "HR"};
        return sectors[random.nextInt(sectors.length)];
    }

    private int getRandomTileCount(NPCCompany company) {
        int availableCash = company.getStats().get(DataType.CASH);
        int maxTiles = availableCash / TILE_COST;
        if (maxTiles <= 0) {
            return 0;
        }
        return random.nextInt(maxTiles) + 1;
    }

    public void performRandomAction(NPCCompany company, Tile[][] grid, Event.EventType currentEvent) {
        int availableCash = company.getStats().get(DataType.CASH);
        boolean canInvest = availableCash > 0;
        boolean canBuyTiles = availableCash >= TILE_COST;
        int availableTiles = company.getStats().get(DataType.TILES);

        boolean isPositiveEffect = currentEvent == Event.EventType.EXPANSION ||
                currentEvent == Event.EventType.INNOVATION ||
                currentEvent == Event.EventType.COMPETITION;

        boolean isNegativeEffect = currentEvent == Event.EventType.PANDEMIC ||
                currentEvent == Event.EventType.RECESSION ||
                currentEvent == Event.EventType.WAR;

        if (canInvest || canBuyTiles) {
            if (isPositiveEffect && canInvest) {
                invest(0, "", company);
            } else if (isNegativeEffect && canBuyTiles) {
                int numTilesToPurchase = getRandomTileCount(company);
                tiles(numTilesToPurchase, "Purchase", company, grid);
            } else {
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
                    int numTilesToPurchase = getRandomTileCount(company);
                    tiles(numTilesToPurchase, "Purchase", company, grid);
                }
            }
        } else if (availableTiles > 0) {
            tiles(0, "Sell", company, grid);
        }
    }

    public void invest(int amount, String sector, NPCCompany company) {
        int randomAmount = getRandomAmount(company);
        String randomSector = getRandomSector();

        System.out.println("NPC DECISION: INVESTING: " + randomAmount +
                ", in sector: " + randomSector +
                ", of company: " + company.getName());
        this.stats = company.getStats();

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
        company.setStats(this.stats);
    }

    public void tiles(int numTile, String method, NPCCompany company, Tile[][] grid) {
        System.out.println("NPC DECISION: " + method + " " + numTile +
                " tiles for company: " + company.getName());
        this.stats = company.getStats();

        boolean success = switch (method) {
            case "Purchase" -> purchaseTiles(numTile, grid, company.getTileType(), company);
            case "Sell" -> sellTiles(numTile, grid, company);
            default -> false;
        };
        if (!success) {
            System.out.println(" Failed to " + method + " " + numTile +
                    "tiles for company: " + company.getName());
        } else {
            System.out.println("Success!" + "\n");
        }
        company.setStats(this.stats);
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
        if (!handleCash(-amount)) {
            return false;
        }
        double increase = amount / 100.0;
        int multiplier = this.stats.get(DataType.MULTIPLIER);
        if (lastInvestment == DataType.MULTIPLIER) {
            this.stats.put(DataType.MULTIPLIER, (int) (multiplier + (2 * (1 - Math.pow(0.5, increase)))));
        } else {
            this.stats.put(DataType.MULTIPLIER, multiplier + (2 * (int)increase));
        }
        lastInvestment = DataType.MULTIPLIER;
        return true;
    }
    /**
     * handles R&D investment
     * @param amount amount to invest - each $100 leads to a $10 price increase
     * @return whether the action was successful
     */
    private boolean investRD(int amount) {
        if (!handleCash(-amount)) {
            return false;
        }
        double increase = amount / 100.0;
        int price = this.stats.get(DataType.PRICE);
        if (lastInvestment == DataType.PRICE) {
            this.stats.put(DataType.PRICE, (int) (price + (20 * (1 - Math.pow(0.5, increase)))));
        } else {
            this.stats.put(DataType.PRICE, price + (20 * (int)increase));
        }
        lastInvestment = DataType.PRICE;
        return true;
    }

    /**
     * handles Raw Goods investment
     * @param amount amount to invest - each $100 leads to 1 unit capacity increase
     * @return whether the action was successful
     */
    private boolean investGoods(int amount) {
        if (!handleCash(-amount)) {
            return false;
        }
        double increase = amount / 100.0;
        int capacity = this.stats.get(DataType.CAPACITY);
        if (lastInvestment == DataType.CAPACITY) {
            this.stats.put(DataType.CAPACITY, (int) (capacity + (2 * (1 - Math.pow(0.5, increase)))));
        } else {
            this.stats.put(DataType.CAPACITY, capacity + (2 * (int)increase));
        }
        lastInvestment = DataType.CAPACITY;
        return true;
    }
    /**
     * handles Human Capital investment
     * @param amount amount to invest - each $100 leads to $3 cost decrease
     * @return whether the action was successful
     */
    private boolean investHumanCapital(int amount) {
        if (!handleCash(-amount)) {
            return false;
        }
        double decrease = amount / 100.0;
        int cost = this.stats.get(DataType.COST);
        if (lastInvestment == DataType.COST) {
            this.stats.put(DataType.COST, (int) (cost - (6 * (1 - Math.pow(0.5, decrease)))));
        } else {
            this.stats.put(DataType.COST, cost - (6 * (int)decrease));
        }
        lastInvestment = DataType.COST;
        return true;
    }

    /**
     * handles purchasing of tiles
     * @param numTiles number of tiles to purchase - each tile is $300
     * @return whether action was successful
     */
    private boolean purchaseTiles(int numTiles, Tile[][] grid, Tile.TileType tileType, Company company) {
        int cost = numTiles * TILE_COST;
        if(!handleCash(-cost)) {
            return false;
        }

        int gridSize = grid.length;

        int startX = 0;
        int startY = 0;

        //check which npc this is by tileType
        double squareSize = (double) Game.GRID_SIZE_X / Game.X_SIZE;
        int arrayEndIdx = (int) squareSize - 1;
        if (tileType == Tile.TileType.CLAIMED_P2) {
            startY = arrayEndIdx;
        } else if (tileType == Tile.TileType.CLAIMED_P3) {
            startX = arrayEndIdx;
        } else if (tileType == Tile.TileType.CLAIMED_P4) {
            startX = arrayEndIdx;
            startY = arrayEndIdx;
        }

        int tilesPurchased = 0;

        for (int i = 0; i < numTiles; i++) {
            Point2D newTile = findTheNextTile(startX, startY, tileType, grid);

            //find an adjacent tile
            if (newTile.getX() != -1) {
                tilesPurchased++;

                grid[(int)newTile.getX()][(int)newTile.getY()].setType(tileType);

                //add the tile to the company's stack
                company.addToStack(newTile);

                //no adjacent tiles available, iterate over the grid to find an empty tile
            } else {
                boolean findAnAvailableTile = false;
                for (Tile[] tiles : grid) {
                    for (int k = 0; k < gridSize; k++) {
                        if (tiles[k].getType() == Tile.TileType.EMPTY) {
                            tiles[k].setType(tileType);
                            tilesPurchased++;
                            //add the tile to the company's stack
                            company.addToStack(newTile);
                            findAnAvailableTile = true;

                        }
                    }
                }

                //grid is already full
                if (!findAnAvailableTile) {
                    int tilesForTheCompany = this.stats.get(DataType.TILES);
                    this.stats.put(DataType.TILES, tilesForTheCompany + tilesPurchased);
                    return false;
                }
            }
        }

        //desired number of tiles purchased
        int tilesForTheCompany = this.stats.get(DataType.TILES);
        this.stats.put(DataType.TILES, tilesForTheCompany + numTiles);
        return true;
    }

    /**
     * handles selling of tiles
     * @param numTiles number of tiles to sell - each tile is worth $100
     * @return whether action was successful
     */
    private boolean sellTiles(int numTiles, Tile[][] grid, Company company) {
        if (this.stats.get(DataType.TILES) < numTiles) {
            return false;
        }
        int profit = numTiles * TILE_VALUE;
        handleCash(profit);
        this.stats.put(DataType.TILES, this.stats.get(DataType.TILES) - numTiles);
        for (int i = 0; i < numTiles; i++) {
            Point2D tileToBeRemoved = company.popFromStack();
            grid[(int)tileToBeRemoved.getX()][(int)tileToBeRemoved.getY()].setType(Tile.TileType.LOST);
        }
        return true;
    }

    /**
     * BFS implementation to find the next tile to buy
     * @param startingX starting x coord
     * @param startingY starting y coord
     * @param playerType the player type to find tiles for
     * @param grid the tileGrid
     * @return the Point2D coord of the tile
     */
    private Point2D findTheNextTile(int startingX, int startingY, Tile.TileType playerType, Tile[][] grid) {
        Queue<Point2D> q = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid.length];

        q.add(new Point2D(startingX, startingY));

        while (!q.isEmpty()) {
            Point2D currCoor = q.poll();

            for (int i = 0; i < 4; i++) {
                int adjX = (int)currCoor.getX() + Game.ROW_OFFSET[i];
                int adjY = (int)currCoor.getY() + Game.COL_OFFSET[i];

                //check if it's in bound
                if (adjX < 0 || adjY < 0 || adjX >= grid.length || adjY >= grid.length || visited[adjX][adjY]) {
                    continue;
                }

                // in bound, create a
                Tile adjTile = grid[adjX][adjY];
                //if the current tile's neighbor is also a tile of the current player
                if (adjTile.getType() == playerType) {
                    Point2D adj = new Point2D(adjX, adjY);
                    q.add(adj);
                    visited[adjX][adjY] = true;

                    //if the current tile(already occupied by the player) has an empty neighbor return it!
                } else if (adjTile.getType() == Tile.TileType.EMPTY || adjTile.getType() == Tile.TileType.LOST) {
                    return new Point2D(adjX, adjY);
                }
            }
        }
        return new Point2D(-1, -1);
    }

}
