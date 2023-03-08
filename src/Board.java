import java.util.ArrayList;
import java.util.Random;

public class Board {

    /** Size of the game-board */
    private int boardSize;
    /** Game-board itself */
    public BoardValues[][] gameBoard;
    /** Array to store used cells */
    public ArrayList<Integer> squareList = new ArrayList<>();

    /**
     * Sets n*n square size
     * @param size Size of our game-board
     */
    public void setBoardSize(int size) {
        this.boardSize = size;
    }

    /**
     * Locks random cells in the board
     * @param nLockedSquares Amount of locked squares in board
     */
    public void squareLocker(int nLockedSquares) {
        Random rand = new Random();

        for (int i = 0; i < (boardSize * boardSize); i++) {
            squareList.add(i);
        }
        for (int i = 0; i < nLockedSquares; i++) {
            int temp = rand.nextInt(squareList.size());
            int squareNum = squareList.get(temp); // 15
            squareList.remove(temp);

            temp = 0; // 1 2 3
            while (squareNum >= boardSize) { // 11 7 3
                temp++;
                squareNum -= boardSize;
            }
            gameBoard[squareNum][temp] = BoardValues.BLOCKED;
        }
    }

    /**
     * Creates the board
     * @param nLockedSquares Amount of locked squares in board
     */
    public void createGameBoard(int nLockedSquares) {
        gameBoard = new BoardValues[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = BoardValues.EMPTY;
            }
        }
        squareLocker(nLockedSquares);
    }

    /**
     * Prints the game-board :]
     */
    public void printBoard() {
        StringBuilder temp = new StringBuilder("  ");

        for (int i = 0; i < boardSize; i++) {
            temp.append("   ").append(i + 1);
        }
        System.out.println(temp);
        temp = new StringBuilder("\n   ");
        temp.append("----".repeat(boardSize));

        for (int i = 0; i < boardSize; i++) {
            System.out.printf("%-2d |", i + 1);
            for (int j = 0; j < boardSize; j++) {
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
