import java.util.ArrayList;
import java.util.Random;

public class Board {

    private int boardSize;
    public BoardValues[][] gameBoard;
    private final ArrayList<Integer> squareList = new ArrayList<>();

    /**
     * Sets n*n square size
     *
     * @param size Size of our game-board
     */
    public void setBoardSize(int size) {
        this.boardSize = size;
    }

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
     * Prints the game-board
     *
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
                System.out.printf(" %c |", translator(gameBoard[i][j]));
            }
            System.out.println(temp.toString() + '-');
        }
    }

    /**
     * Turns enum to char for printBoard func
     *
     * @param value A value from BoardValues
     * @return A char showing that square's status
     */
    private char translator(BoardValues value) {
        switch (value) {
            case BLOCKED -> {
                return '#';
            }
            case EMPTY -> {
                return '-';
            }
            case X -> {
                return 'X';
            }
            case O -> {
                return 'O';
            }
            default -> {
                return 'F';
            }
        }
    }
}

enum BoardValues {
    EMPTY,
    BLOCKED,
    X,
    O
}
