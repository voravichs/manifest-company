package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

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

    public Game(int xSize, int ySize) {
        // Creates a tile array of x*y size
        tileGrid = new Tile[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
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

        // TODO: init stats for each player
        // TODO: Add actual company names
        Company player1 = new UserCompany("player", new CompanyActionImpl());
        Company npc1 = new NPCompany("NPC1", new CompanyActionImpl());
        Company npc2 = new NPCompany("NPC2", new CompanyActionImpl());
        Company npc3 = new NPCompany("NPC3", new CompanyActionImpl());

        // TODO: REMOVE, TESTING METHODS
        player1.invest(1000, "marketing");
        npc1.invest(2000, "HR");
        npc2.invest(3000, "goods");
        npc3.invest(4000, "hiring");

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

    public void nextTurn() {
        // TODO: FILL THIS OUT, BFS, ETC
        // TODO: PLACEHOLDER: claim hardcoded tiles
        int arrayEndIdx = (int) squareSize - 1;
        claimTile(0,1, Tile.TileType.CLAIMED_P1);
        claimTile(0,arrayEndIdx - 1, Tile.TileType.CLAIMED_P2);
        claimTile(arrayEndIdx - 1,0, Tile.TileType.CLAIMED_P3);
        claimTile(arrayEndIdx - 1,arrayEndIdx, Tile.TileType.CLAIMED_P4);

        // FOR NOW, JUST
    }

    public void investIn(int num) {
        switch (num) {
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            case 3:
                System.out.println(3);
                break;
            case 4:
                System.out.println(4);
                break;
            default:
                System.out.println(1);
                break;
        }
    }

}
