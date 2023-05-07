package mc.manifestcompany.gui;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a logical and graphical tile in the game.
 * @author Team Manifest Company
 */
public class Tile extends FXShape {

    private TileType type;

    public Tile(Rectangle square, Point2D location, TileType type) {
        super(square, location);
        this.type = type;
        setType(type);
        setTilePosition();
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
        this.square.setFill(this.type.fill());
        this.square.setStroke(Color.BLACK);
    }

    public enum TileType {
        EMPTY(Color.WHITE),
        CLAIMED_P1(Color.AQUAMARINE),
        CLAIMED_P2(Color.LIGHTGREEN),
        CLAIMED_P3(Color.TOMATO),
        CLAIMED_P4(Color.YELLOW),
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
