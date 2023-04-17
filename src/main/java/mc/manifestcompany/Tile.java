package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a logical and graphical tile in the game.
 */
public class Tile extends FXShape {

    private TileType type;
    public Tile(Rectangle square, Point2D location, TileType type) {
        super(square, location);
        this.type = type;
        setTileColor();
        setTilePosition();
    }

    /**
     * Sets the color of the tile in the GUI.
     */
    public void setTileColor() {
        this.square.setFill(this.type.fill());
        this.square.setStroke(Color.BLACK);
    }

    /**
     * Sets the position of the tile in the GUI.
     */
    private void setTilePosition() {
        this.square.setX(this.location.getX());
        this.square.setY(this.location.getY());
    }

    /**
     * @return the TileType of the current tile
     */
    public TileType getType() {
        return type;
    }

    /**
     * Sets the type of the tile, then colors the tile.
     * @param type TileType to set this tile to.
     */
    public void setType(TileType type) {
        this.type = type;
        setTileColor();
    }

    public enum TileType {
        EMPTY(Color.WHITE),
        CLAIMED_P1(Color.AQUAMARINE),
        CLAIMED_P2(Color.LIGHTGREEN),
        CLAIMED_P3(Color.TOMATO),
        CLAIMED_P4(Color.LIGHTCORAL),
        LOST(Color.RED);

        private final Color fill;

        TileType(Color fill) {
            this.fill = fill;
        }

        private Color fill() {
            return fill;
        }
    }
}
