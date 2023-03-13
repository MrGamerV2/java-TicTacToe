import java.util.Scanner;

public class Player {

    /**     Stores player input for later usage     */
    public int yInput, xInput;
    /**     is the player [ X ]?    */
    public boolean isPlayerX;
    private final short timeOutInMil = 700;

    public Player(boolean isPlayerX) {
        if (isPlayerX) this.isPlayerX = true;
    }

    /**     Gets input and stores in local variables        */
    public boolean getInput() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        scan.reset();
        if (isPlayerX) System.out.print(" It's [ X ] turn:  \n yAxes: ");
        else System.out.print(" It's [ O ] turn:  \n yAxes: ");

        int temp;
        try {
            temp = Integer.parseInt(scan.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(" !! WRONG INPUT DETECTED !!");
            Thread.sleep(timeOutInMil);
            return false;
        }
        yInput = temp;

        System.out.print(" xAxes: ");
        try {
            temp = Integer.parseInt(scan.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(" !! WRONG INPUT DETECTED !!");
            Thread.sleep(timeOutInMil);
            return false;
        }
        xInput = temp;
        return true;
    }


    /**
     * Tries to fill the selected
     * @param gameBoard The game-board itself
     * @param boardSize Size of the game-board
     * @return True if cell was filled successfully
     * @throws InterruptedException Still, no idea.
     */
    public boolean fillCell(BoardValues[][] gameBoard, int boardSize) throws InterruptedException {
        if ((yInput < 0 || yInput > boardSize - 1) || (xInput < 0 || xInput > boardSize - 1)) {
            System.out.println(" !! SELECTED SQUARE OUT OF BOUND !!");
            Thread.sleep(timeOutInMil);
            return false;
        } else {
            if (gameBoard[yInput][xInput] != BoardValues.EMPTY) {
                System.out.println(" !! SELECTED SQUARE IS FULL !!");
                Thread.sleep(timeOutInMil);
                return false;
            } else if (isPlayerX) {
                gameBoard[yInput][xInput] = BoardValues.X;
                return true;
            } else {
                gameBoard[yInput][xInput] = BoardValues.O;
                return true;
            }
        }
    }

    /**
     * @param gameBoard The game-board itself
     * @return True if a player has won the game
     */
    public boolean winLogic(BoardValues[][] gameBoard) {
        if (verticalCounter(gameBoard)) return true;
        else if (horizontalCounter(gameBoard)) return true;
        else if (diagonalCounterTR(gameBoard)) return true;
        else return diagonalCounterTL(gameBoard);
    }


    private boolean verticalCounter(BoardValues[][] gameBoard) {
        int count = 1;
        int yTemp = yInput - 1;

        if (yInput > 0) { // CHECKS UPWARD
            while (gameBoard[yInput][xInput] == gameBoard[yTemp][xInput]) {
                count++;
                yTemp--;
                if (yTemp < 0) break;
            }
        }
        yTemp = yInput + 1;
        if (yInput < Settings.boardSize - 1) { // CHECKS DOWNWARD
            while (gameBoard[yInput][xInput] == gameBoard[yTemp][xInput]) {
                count++;
                yTemp++;
                if (yTemp >= Settings.boardSize) break;
            }
        }
        return (count >= Settings.alignInARow);
    }

    private boolean horizontalCounter(BoardValues[][] gameBoard) {
        int count = 1;
        int xTemp = xInput + 1;

        if (xInput < Settings.boardSize - 1) { // CHECKS RIGHT SIDE
            while (gameBoard[yInput][xInput] == gameBoard[yInput][xTemp]) {
                count++;
                xTemp++;
                if (xTemp >= Settings.boardSize) break;
            }
        }
        xTemp = xInput - 1;
        if (xInput > 0) { // CHECK LEFT SIDE
            while (gameBoard[yInput][xInput] == gameBoard[yInput][xTemp]) {
                count++;
                xTemp--;
                if (xTemp < 0) break;
            }
        }
        return (count >= Settings.alignInARow);
    }

    private boolean diagonalCounterTR(BoardValues[][] gameBoard) {
        int count = 1;

        int xTemp = xInput + 1;
        int yTemp = yInput - 1;
        if (xInput < Settings.boardSize - 1 && yInput > 0) { // CHECKS TOP RIGHT
            while (gameBoard[yInput][xInput] == gameBoard[yTemp][xTemp]) {
                count++;
                xTemp++;
                yTemp--;
                if (xTemp >= Settings.boardSize || yTemp < 0) break;
            }
        }
        xTemp = xInput - 1;
        yTemp = yInput + 1;
        if (xInput > 0 && yInput < Settings.boardSize - 1) { // CHECKS BOTTOM LEFT
            while (gameBoard[yInput][xInput] == gameBoard[yTemp][xTemp]) {
                count++;
                xTemp--;
                yTemp++;
                if (xTemp < 0 || yTemp >= Settings.boardSize) break;
            }
        }
        return (count >= Settings.alignInARow);
    }

    private boolean diagonalCounterTL(BoardValues[][] gameBoard) {
        int count = 1;

        int xTemp = xInput - 1;
        int yTemp = yInput - 1;
        if (yInput > 0 && xInput > 0) { // CHECKS TOP LEFT
            while (gameBoard[yInput][xInput] == gameBoard[yTemp][xTemp]) {
                count++;
                xTemp--;
                yTemp--;
                if (xTemp < 0 || yTemp < 0) break;
            }
        }
        xTemp = xInput + 1;
        yTemp = yInput + 1;
        if (yInput < Settings.boardSize - 1 && xInput < Settings.boardSize - 1) { // CHECKS BOTTOM LEFT
            while (gameBoard[yInput][xInput] == gameBoard[yTemp][xTemp]) {
                count++;
                xTemp++;
                yTemp++;
                if (xTemp >= Settings.boardSize || yTemp >= Settings.boardSize) break;
            }
        }
        return count >= Settings.alignInARow;
    }
}


