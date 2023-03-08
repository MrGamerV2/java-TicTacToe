public class Player {

    /** Stores player input for later usage */
    public int xInput, yInput;
    /** is the player [ X ]?         IT'LL BE CHANGED LATER*/
    public boolean isPlayerX;
    private final short timeOutInMil = 700;

    public Player(boolean isPlayerX) {
        if (isPlayerX) this.isPlayerX = true;
    }

    /** Gets input and stores in local variables */
    public boolean getInput() throws InterruptedException {

        if (isPlayerX) System.out.print(" It's [ X ] turn:  \n xAxes: ");
        else System.out.print(" It's [ O ] turn:  \n xAxes: ");

        int temp;
        try {
            temp = Integer.parseInt(App.scan.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(" !! WRONG INPUT DETECTED !!");
            Thread.sleep(timeOutInMil);
            return false;
        }
        xInput = temp;

        System.out.print(" yAxes: ");
        try {
            temp = Integer.parseInt(App.scan.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(" !! WRONG INPUT DETECTED !!");
            Thread.sleep(timeOutInMil);
            return false;
        }
        yInput = temp;

        return true;
    }


    /** Tries to fill the selected
     * @param gameBoard The game-board itself
     * @param boardSize Size of the game-board
     * @return True if cell was filled successfully
     * @throws InterruptedException Still, no idea.
     */
    public boolean fillCell(BoardValues[][] gameBoard, int boardSize) throws InterruptedException {

        if ((xInput < 0 || xInput > boardSize - 1) || (yInput < 0 || yInput > boardSize - 1)) {
            System.out.println(" !! SELECTED SQUARE OUT OF BOUND !!");
            Thread.sleep(timeOutInMil);
            return false;
        } else {
            if (gameBoard[xInput][yInput] != BoardValues.EMPTY) {
                System.out.println(" !! SELECTED SQUARE IS FULL !!");
                Thread.sleep(timeOutInMil);
                return false;
            } else if (isPlayerX) {
                gameBoard[xInput][yInput] = BoardValues.X;
                return true;
            } else {
                gameBoard[xInput][yInput] = BoardValues.O;
                return true;
            }
        }
    }

    /**
     * @param gameBoard The game-board itself
     * @param boardSize Size of square game-board
     * @param alignNtoWin Amount of locked cells in the board
     * @return True if a player has won the game
     */
    public boolean winLogic(BoardValues[][] gameBoard, int boardSize, int alignNtoWin) {
        if (verticalCounter(gameBoard, boardSize, alignNtoWin)) return true;
        else if (horizontalCounter(gameBoard, boardSize, alignNtoWin)) return true;
        else if (diagonalCounterTR(gameBoard, boardSize, alignNtoWin)) return true;
        else return diagonalCounterTL(gameBoard, boardSize, alignNtoWin);
    }

    
    private boolean verticalCounter(BoardValues[][] gameBoard, int boardSize, int alignNtoWin) {
        int count = 1;
        int temp = yInput - 1;

        if (yInput > 0) { // CHECKS UP
            while (gameBoard[xInput][yInput] == gameBoard[xInput][temp]) {
                count++;
                temp--;
                if (temp == -1) break;
            }
        }
        temp = yInput + 1;
        if (yInput < boardSize - 1) { // CHECKS DOWN
            while (gameBoard[xInput][yInput] == gameBoard[xInput][temp]) {
                count++;
                temp++;
                if (temp == boardSize) break;
            }
        }
        return count >= alignNtoWin;
    }

    private boolean horizontalCounter(BoardValues[][] gameBoard, int boardSize, int alignNtoWin) {
        int count = 1;
        int temp = xInput - 1;

        if (xInput > 0) { // CHECKS RIGHT
            while (gameBoard[xInput][yInput] == gameBoard[temp][yInput]) {
                count++;
                temp++;
                if (temp == boardSize) break;
            }
        }
        temp = xInput + 1;
        if (xInput < boardSize - 1) { // CHECK LEFT
            while (gameBoard[xInput][yInput] == gameBoard[temp][yInput]) {
                count++;
                temp--;
                if (temp == -1) break;
            }
        }
        return count >= alignNtoWin;
    }

    private boolean diagonalCounterTR(BoardValues[][] gameBoard, int boardSize, int alignNtoWin) {
        int count = 1;
        int xTemp = xInput + 1;
        int yTemp = yInput - 1;

        if (xInput < boardSize - 1 && yInput > 0) { // CHECKS TOP RIGHT
            while (gameBoard[xInput][yInput] == gameBoard[xTemp][yTemp]) {
                count++;
                xTemp++;
                yTemp--;
                if (xTemp == boardSize || yTemp == -1) break;
            }
        }
        xTemp = xInput - 1;
        yTemp = yInput + 1;
        if (xInput > 0 && yInput < boardSize - 1) { // CHECKS BOTTOM LEFT
            while (gameBoard[xInput][yInput] == gameBoard[xTemp][yTemp]) {
                count++;
                xTemp--;
                yTemp++;
                if (xTemp == -1 || yTemp == boardSize) break;
            }
        }

        return count >= alignNtoWin;
    }

    private boolean diagonalCounterTL(BoardValues[][] gameBoard, int boardSize, int alignNtoWin) {
        int count = 1;
        int xTemp = xInput - 1;
        int yTemp = yInput - 1;

        if (xInput > 0 && yInput > 0) { // CHECKS TOP LEFT
            while (gameBoard[xInput][yInput] == gameBoard[xTemp][yTemp]) {
                count++;
                xTemp--;
                yTemp--;
                if (xTemp == -1 || yTemp == -1) break;
            }
        }
        xTemp = xInput + 1;
        yTemp = yInput + 1;
        if (xInput < boardSize - 1 && yInput < boardSize - 1) { // CHECKS BOTTOM LEFT
        while (gameBoard[xInput][yInput] == gameBoard[xTemp][yTemp]) {
                count++;
                xTemp++;
                yTemp++;
                if (xTemp == boardSize || yTemp == boardSize) break;
            }
        }

        return count >= alignNtoWin;
    }
}


