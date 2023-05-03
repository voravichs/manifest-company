package mc.manifestcompany.gamelogic;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.DataType;
import mc.manifestcompany.company.*;
import mc.manifestcompany.gui.Tile;

import java.util.*;

/**
 * Manages the non-GUI, logical components of the game.
 * @author Team Manifest Company
 */
public class Game {
    /* Final Variables */
    public static final int GRID_SIZE_X = 400;

    /* Instance Variables */
    // TileGrid
    private Tile[][] tileGrid;
    private final int xSize, ySize;
    private final double squareSize;
    private final int numTiles;

    // Players and NPCS
    private UserCompany player;
    private Queue<Company> npcQueue;

    // Market demand/price
    // market demand for how much a company can sell, could change if an event happens
    private int marketDemand = 30;
    // market price for the goods, could change if an event happens
    private int marketPrice = 100;

    // Direction vectors for BFS
    static int[] rowOffset = {-1, 0, 1, 0};
    static int[] colOffset = { 0, 1, 0, -1 };


    public Game(int xSize, int ySize) {
        // Creates a tile array of x*y size
        tileGrid = new Tile[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
        this.numTiles = xSize * ySize;
        squareSize = (double) GRID_SIZE_X / xSize;

        // Initializes the tileGrid
        initTileGrid();

        // initializes the players
        initPlayers();
    }

    /**
     * Initializes the tileGrid to have all empty tiles.
     */
    public void initTileGrid() {
        // Initialize the tileGrid
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                tileGrid[x][y] = new Tile(
                        new Rectangle(squareSize,squareSize),
                        new Point2D(x * squareSize, y * squareSize),
                        Tile.TileType.EMPTY);
            }
        }
    }

    /**
     * Initializes the players by granting initial tiles and stats.
     */
    public void initPlayers() {
        // Set the corner squares to players
        int arrayEndIdx = (int) squareSize - 1;
        claimTile(0,0, Tile.TileType.CLAIMED_P1);
        claimTile(0,arrayEndIdx, Tile.TileType.CLAIMED_P2);
        claimTile(arrayEndIdx,0, Tile.TileType.CLAIMED_P3);
        claimTile(arrayEndIdx,arrayEndIdx, Tile.TileType.CLAIMED_P4);

        // TODO: DYNAMIC NAMES AND LINKS
        this.player = new UserCompany("Player", new CompanyActionImpl(), Tile.TileType.CLAIMED_P1, "images/playerfastfood.png");
        Company npc1 = new NPCCompany("WacMondalds", new NPCActionImpl(), Tile.TileType.CLAIMED_P2, "images/wacmonalds.png");
        Company npc2 = new NPCCompany("Queso Queen", new NPCActionImpl(), Tile.TileType.CLAIMED_P3, "images/quesoqueen.png");
        Company npc3 = new NPCCompany("Pizza Shack", new NPCActionImpl(), Tile.TileType.CLAIMED_P4, "images/pizzashack.png");
        this.npcQueue = new ArrayDeque<>();
        this.npcQueue.add(npc1);
        this.npcQueue.add(npc2);
        this.npcQueue.add(npc3);
    }

    /**
     * Claims a tile at a given index in the tileGrid for the playerType.
     * @param x x-coordinate of the tile to claim
     * @param y y-coordinate of the tile to claim
     * @param playerType the player to grant the tile to
     */
    public void claimTile(int x, int y, Tile.TileType playerType) {
        tileGrid[x][y].setType(playerType);
    }

    /**
     * @return the 2D grid of tiles
     */
    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    /**
     * @return the list of companies
     */
    public List<Company> getCompanyList() {
        List<Company> companyList = new ArrayList<>(npcQueue);
        companyList.add(player);
        return companyList;
    }

    /**
     * Updates all the necessary components when user advances the turn
     */
    public void nextTurn() {
//         TODO: PLACEHOLDER: claim hardcoded tiles
//        int arrayEndIdx = (int) squareSize - 1;
//        claimTile(0,1, Tile.TileType.CLAIMED_P1);
//        claimTile(0,arrayEndIdx - 1, Tile.TileType.CLAIMED_P2);
//        claimTile(arrayEndIdx - 1,0, Tile.TileType.CLAIMED_P3);
//        claimTile(arrayEndIdx - 1,arrayEndIdx, Tile.TileType.CLAIMED_P4);

        Turn turn = new TurnImpl(marketDemand, marketPrice);
        int numGoods = turn.randomGoodsSold();

        for (Company npc : npcQueue) {
            NPCCompany npcCompany = (NPCCompany) npc;
            npcCompany.getActions().performRandomAction(npcCompany);
        }

        turn.turn(numGoods, player);
        for (Company npc: npcQueue) {
            turn.turn(numGoods, npc);
        }

        if (!turn.validCompany(player)) {
            // TODO: PLAYER LOST, GAME ENDS
            return;
        }
        // check if any of the NPC went bankrupt, remove from npcQueue if bankrupt
        int numNPCs = npcQueue.size();
        for (int i = 0; i < numNPCs; i++) {
            Company npc = npcQueue.poll();
            if (turn.validCompany(npc)) {
                npcQueue.add(npc);
            }
        }

        Company winner = turn.winner(numTiles, player, npcQueue);
        if (turn.boardFull(numTiles, player, npcQueue) || winner != null) {
            if (winner != null) {
                System.out.println(winner.getName() + " wins!");
            } else {
                System.out.println("Game ended! no player won.");
            }
            // TODO: GAME ENDS
        }

        Event event = new EventImpl();
        Event.EventType eventType = event.randomEvent();
        // TODO: eventType helps NPC make decisions
        if (eventType != Event.EventType.NONE) {
            int [] updated = event.updateMarket(eventType, marketDemand, marketPrice);
            this.marketDemand = updated[0];
            this.marketPrice = updated[1];
        }
    }

    /**
     * Called by game controller when user chooses to invest
     * @param num number to dictate user option
     */
    public void investIn(int num) {
        int amount = 0; // TODO: MISSING INVESTMENT AMOUNT INPUT FROM GUI
        switch (num) {
            case 1:
                System.out.println(1);
                player.invest(amount, "Marketing");
                break;
            case 2:
                System.out.println(2);
                player.invest(amount, "R&D");
                break;
            case 3:
                System.out.println(3);
                player.invest(amount, "Goods");
                break;
            case 4:
                System.out.println(4);
                player.invest(amount, "HR");
                break;
            default:
                System.out.println(1);
                break;
        }
    }

    /**
     * called by game controller when user chooses to buy or sell tiles
     * @param num number to dictate user option
     */
    public void tiles (int num) {
        // Assumes that there will be an event handler in game controller that calls this function when user
        // selects to buy or sell tiles
        int amount = 0; // TODO: MISSING NUM TILES INPUT FROM GUI
        switch (num) {
            case 1:
                System.out.println(1);
                player.tiles(amount, "Purchase", this.tileGrid);
                break;
            case 2:
                System.out.println(2);
                player.tiles(amount, "Sell", this.tileGrid);
                break;
        }
    }

    private Point2D findTheNextTile(int startingX, int startingY, Tile.TileType playerType) {
        Queue<Point2D> q = new LinkedList<>();
        boolean[][] visited = new boolean[this.xSize][this.ySize];

        q.add(new Point2D(startingX, startingY));

        while (!q.isEmpty()) {
            Point2D currCoor = q.poll();

            for (int i = 0; i < 4; i++) {
                int adjX = (int)currCoor.getX() + rowOffset[i];
                int adjY = (int)currCoor.getY() + colOffset[i];

                //check if it's in bound
                if (adjX < 0 || adjY < 0 || adjX >= this.xSize || adjY >= this.ySize || visited[adjX][adjY]) {
                    continue;
                }

                // in bound, create a
                Tile adjTile = tileGrid[adjX][adjY];
                //if the current tile's neighbor is also a tile of the current player
                if (adjTile.getType() == playerType) {
                    Point2D adj = new Point2D(adjX, adjY);
                    q.add(adj);
                    visited[adjX][adjY] = true;

                    //if the current tile(already occupied by the player) has an empty neighbor return it!
                } else if (adjTile.getType() == Tile.TileType.EMPTY) {
                    return new Point2D(adjX, adjY);
                }

            }
        }

        return new Point2D(-1, -1);

    }

    public List<Company> sortCompaniesBy(DataType dataType) {
        List<Company> companyList = getCompanyList();
        companyList.sort(Company.comparatorBy(dataType));

        return companyList;
    }

    public UserCompany getPlayer() {
        return this.player;
    }

    public Queue<Company> getNPCs() {
        return this.npcQueue;
    }

}
