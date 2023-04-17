package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Manages the non-GUI, logical components of the game.
 * @author VoravichS
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
    }

    /**
     * Claims a tile at a certain index in the tileGrid for the playerType.
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

}
