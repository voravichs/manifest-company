package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Represents a graphical square tile in the game.
 * Stores the graphical square and the location of the square.
 */
abstract class FXShape {
    // Instance variables
    protected Rectangle square;
    protected Point2D location;

    public FXShape(Rectangle square, Point2D location) {
        this.square = square;
        this.location = location;
    }

    public Rectangle getSquare() {
        return this.square;
    }

    public Point2D getLocation() {
        return this.location;
    }
}
