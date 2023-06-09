package mc.manifestcompany.company;

import javafx.geometry.Point2D;
import mc.manifestcompany.DataType;
import mc.manifestcompany.gamelogic.Game;
import mc.manifestcompany.gui.Tile;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the company actions interface.
 * @author Team Manifest Company
 */
public class CompanyActionImpl implements CompanyAction {

    protected EnumMap<DataType, Integer> stats;
    protected static final int TILE_COST = 300;
    protected static final int TILE_VALUE = 300;
    private DataType lastInvestment;

    /**
     * Invests the specified amount in the given sector for the specified company.
     * @param amount The amount to invest.
     * @param sector The sector to invest in (Marketing, R&D, Goods, or HR).
     * @param company The company in which to invest.
     */
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

    /**
     * Performs the specified method (Purchase or Sell) for the specified number of tiles for the given company.
     * @param numTile The number of tiles to purchase or sell.
     * @param method The method to use, either "Purchase" or "Sell".
     * @param company The company for which to purchase or sell tiles.
     * @param grid The grid of tiles.
     */
    @Override
    public void tiles(int numTile, String method, Company company, Tile[][] grid) {
        this.stats = company.getStats();
        System.out.println(method + " " + numTile +
                " tiles for company: " + company.getName());
        boolean success = switch (method) {
            case "Purchase" -> purchaseTiles(numTile, grid, company.getTileType(), company);
            case "Sell" -> sellTiles(numTile, grid, company);
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
        int gridSize = grid.length;
        int cost = numTiles * TILE_COST;
        if(!handleCash(-cost)) {
            return false;
        }

        int startX = 0;
        int startY = 0;

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
     * @param numTiles number of tiles to sell - each tile is worth $300
     * @return whether action was successful
     */
    private boolean sellTiles(int numTiles, Tile[][] grid, Company company) {
        if (this.stats.get(DataType.TILES) < numTiles) {
            return false;
        }
        int profit = -numTiles * TILE_VALUE;
        handleCash(profit);
        this.stats.put(DataType.TILES, this.stats.get(DataType.TILES) + numTiles);

        for (int i = 0; i < -numTiles; i++) {
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
