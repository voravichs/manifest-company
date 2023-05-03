package mc.manifestcompany;

import mc.manifestcompany.company.Company;
import mc.manifestcompany.gui.Tile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FileHandler {
    public static void save(Tile[][] tileMap, List<Company> companyList, String saveName) throws IOException {
        // Init files and writer
        String filePath = "saveFiles/" + saveName + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        // Write contents of tileMap
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap[x].length; y++) {
                // Format:
                // <tileType> E, 1, 2, 3, 4, or L
                // Empty, players 1 thru 4, or Lost
                switch (tileMap[x][y].getType()) {
                    case EMPTY -> writer.write("E ");
                    case CLAIMED_P1 -> writer.write("1 ");
                    case CLAIMED_P2 -> writer.write("2 ");
                    case CLAIMED_P3 -> writer.write("3 ");
                    case CLAIMED_P4 -> writer.write("4 ");
                    case LOST -> writer.write("L ");
                }
            }
            writer.write("\n");
        }

        // Write contents of company List
        for (Company company:
             companyList) {
            // Format:
            // <PRICE> <MULTIPLIER> <CAPACITY> <COST> <CASH> <TILES>
            HashMap<Enum<DataType>, Integer> stats = company.getStats();
            writer.write(stats.get(DataType.PRICE) + " ");
            writer.write(stats.get(DataType.MULTIPLIER) + " ");
            writer.write(stats.get(DataType.CAPACITY) + " ");
            writer.write(stats.get(DataType.COST) + " ");
            writer.write(stats.get(DataType.CASH) + " ");
            writer.write(stats.get(DataType.TILES) + " ");
            writer.write("\n");
        }

        writer.close();
    }
}
