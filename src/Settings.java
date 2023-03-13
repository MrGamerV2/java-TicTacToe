import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Settings {

    /**
     * boardSize : Size of the game-board
     * lockedCells : Amount of locked squares in board
     * alignNtoWin : Amount of squares needed to be aligned to win
     */
    public static int boardSize = 4;
    public static int lockedCells = 3;
    public static int alignInARow = 3;

    /**         Loading pre-set settings OR Creates a new file with default value of settings           */
    public static void readSettings() throws IOException {
        File settingFile = new File("./AppData.txt");
        if (settingFile.exists()) {
            Scanner scanner = new Scanner(settingFile);
            scanner.useDelimiter("[a-z|A-Z]*\\s*#\\s");
            scanner.nextLine(); // SKIPS THE COMMENT
            for (int i = 1; i < 4; i++) {
                String buffer = "";
                try {
                    buffer = scanner.next().strip();
                } catch (NoSuchElementException err){
                    scanner.close();
                    settingFile.delete();
                    System.err.println(" AppData.txt HAS BEEN WRONGFULLY MODIFIED, RESTART THE PROGRAM ");
                    System.exit(85);
                }
                try {
                    switch (i) {
                        case 1 -> boardSize = Integer.parseInt(buffer);
                        case 2 -> lockedCells = Integer.parseInt(buffer);
                        case 3 -> alignInARow = Integer.parseInt(buffer);
                    }
                } catch (NumberFormatException err){
                    scanner.close();
                    settingFile.delete();
                    System.err.println(" AppData.txt HAS BEEN WRONGFULLY MODIFIED, RESTART THE PROGRAM ");
                    System.exit(85);
                }
            }
            scanner.close();
        } else {
            if (settingFile.createNewFile()) {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(settingFile));
                fileWriter.write("// DO NOT CHANGE ANYTHING BEFORE HASHTAGS\n");
                fileWriter.write("BoardSize   # " + boardSize + '\n');
                fileWriter.write("LockedCells # " + lockedCells + '\n');
                fileWriter.write("AlignInARow # " + alignInARow + '\n');
                fileWriter.close();
            } else {
                System.err.println(" PERMISSION WAS DENIED ");
                System.exit(85);
            }
        }
    }

    /**      Updates AppData.txt file           */
    public static void writeSettings() throws IOException {
        File settingFile = new File("src/AppData.txt");
        if (settingFile.delete()) {
            if (settingFile.createNewFile()) {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(settingFile));
                fileWriter.write("// DO NOT CHANGE ANYTHING BEFORE HASHTAGS\n");
                fileWriter.write("BoardSize   # " + boardSize + '\n');
                fileWriter.write("LockedCells # " + lockedCells + '\n');
                fileWriter.write("AlignInARow # " + alignInARow + '\n');
                fileWriter.close();
            } else {
                System.err.println(" SETTINGS FILE COULD NOT BE UPDATED ");
                System.exit(1);
            }
        } else {
            System.err.println(" SETTINGS FILE COULD NOT BE DELETED ");
            System.exit(1);
        }
    }
}
