package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.Queue;

/**
 * Manages the non-GUI, logical components of the game.
 * @author Team Manifest Company
 */
public class Game {
    /* Final Variables */
    public static final int GRID_SIZE_X = 400;

    /* Instance Variables */
    private Tile[][] tileGrid;
    private final int xSize, ySize;
    private final double squareSize;
    private final int numTiles;

    private UserCompany player;
    private Queue<Company> npcQueue;

    private static int marketDemand = 30;  // market demand for how much a company can sell, could change if an event happens
    private static int marketPrice = 100; // market price for the goods, could change if an event happens


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

        // TODO: Add actual company names
        this.player = new UserCompany("player", new CompanyActionImpl(), new CompanyStatsImpl());
        Company npc1 = new NPCompany("NPC1", new CompanyActionImpl(), new CompanyStatsImpl());
        Company npc2 = new NPCompany("NPC2", new CompanyActionImpl(), new CompanyStatsImpl());
        Company npc3 = new NPCompany("NPC3", new CompanyActionImpl(), new CompanyStatsImpl());
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

        // TODO: NPC MAKES INVESTMENT DECISIONS & TILE DECISIONS;

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

        if (!turn.validGame(numTiles, player, npcQueue)) {
            Company winner = turn.winner(numTiles, player, npcQueue);
            if(winner == null) {
                System.out.println("Game ended! no player won.");
            } else {
                System.out.println(winner.getName() + " wins!");
            }
            // TODO: GAME ENDS
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
                player.tiles(amount, "Purchase");
                break;
            case 2:
                System.out.println(2);
                player.tiles(amount, "Sell");
                break;
        }

    }

}
