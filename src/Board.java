import java.util.ArrayList;
import java.util.Random;

public class Board {

    /**     Game-board itself    */
    public BoardValues[][] gameBoard;
    /**     Array to store used cells    */
    public ArrayList<Integer> squareList = new ArrayList<>();


    /**     Locks random cells in the board     */
    public void squareLocker() {
        Random rand = new Random();

        for (int i = 0; i < (Settings.boardSize * Settings.boardSize); i++) {
            squareList.add(i);
        }

        for (int i = 0; i < Settings.lockedCells; i++) {
            int temp = rand.nextInt(squareList.size()); // KEEPS THE INDEX
            int squareNum = squareList.get(temp);
            squareList.remove(temp);

            temp = 0; // TURNS TO TEMP Y AXES
            while (squareNum >= Settings.boardSize) {
                temp++;
                squareNum -= Settings.boardSize;
            }
            gameBoard[temp][squareNum] = BoardValues.BLOCKED;
        }
    }

    /**     Creates the board       */
    public void createGameBoard() {
        gameBoard = new BoardValues[Settings.boardSize][Settings.boardSize];

        for (int i = 0; i < Settings.boardSize; i++) {
            for (int j = 0; j < Settings.boardSize; j++) {
                gameBoard[i][j] = BoardValues.EMPTY;
            }
        }
        squareLocker();
    }

    /**     Prints the game-board :]        */
    public void printBoard() {
        StringBuilder temp = new StringBuilder("  ");

        for (int i = 0; i < Settings.boardSize; i++) {
            temp.append("   ").append(i + 1);
        }
        System.out.println(temp);
        temp = new StringBuilder("\n   ");
        temp.append("----".repeat(Settings.boardSize));

        for (int i = 0; i < Settings.boardSize; i++) {
            System.out.printf("%-2d |", i + 1);
            for (int j = 0; j < Settings.boardSize; j++) {
                System.out.printf(" %s |", (translator(gameBoard[i][j]) + "\033[0m"));
            }
            System.out.println(temp.toString() + '-');
        }
    }

    /**
     * Turns enum to char for printBoard func
     * @param value A value from BoardValues
     * @return A colored char showing what's in that cell
     */
    private String translator(BoardValues value) {
        switch (value) {
            case BLOCKED -> {
                return "\033[1;31m" + "#"; // It's RED
            }
            case EMPTY -> {
                return "\033[1;30m" + "-"; // It's BLACK
            }
            case X -> {
                return "\033[1;37m" + "X"; // It's WHITE
            }
            case O -> {
                return "\033[1;34m" + "O"; // It's BLUE
            }
            default -> {
                return "F";
            }
        }
    }
}

enum BoardValues {
    EMPTY, BLOCKED, X, O
}
