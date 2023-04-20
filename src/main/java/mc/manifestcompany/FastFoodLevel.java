package mc.manifestcompany;

/**
 * Enum defining fast food companies and their image links.
 * @author Team Manifest Company
 */
public enum FastFoodLevel {
    PLAYER("Player", "images/playerfastfood.png"),
    WACMONDALDS("WacMondalds", "images/wacmonalds.png"),
    QUESO_QUEEN("Queso Queen", "images/quesoqueen.png"),
    PIZZA_SHACK("Pizza Shack", "images/pizzashack.png");

    private final String name;
    private final String imageLink;

    FastFoodLevel(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }
}
