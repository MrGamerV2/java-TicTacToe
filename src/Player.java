public class Player {
    public int xInput;
    public int yInput;
    public boolean isPlayerX;

    public Player(boolean isPlayerX) {
        if (isPlayerX) this.isPlayerX = true;
    }

    public boolean getInput() throws InterruptedException {

        if (isPlayerX) System.out.print(" It's [ X ] turn:  \n xAxes: ");
        else System.out.print(" It's [ O ] turn:  \n xAxes: ");

        int temp;
        try {
            temp = Integer.parseInt(App.scan.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(" !! WRONG INPUT DETECTED !!");
            Thread.sleep(500);
            return false;
        }
        xInput = temp;

        System.out.print(" yAxes: ");
        try {
            temp = Integer.parseInt(App.scan.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(" !! WRONG INPUT DETECTED !!");
            Thread.sleep(500);
            return false;
        }
        yInput = temp;

        return true;
    }

    public boolean fillSquare(BoardValues[][] gameBoard, int boardSize) throws InterruptedException {

        if ((xInput < 0 || xInput > boardSize - 1) || (yInput < 0 || yInput > boardSize - 1)) {
            System.out.println(" !! SELECTED SQUARE OUT OF BOUND !!");
            Thread.sleep(500);
            return false;
        } else {
            if (gameBoard[xInput][yInput] != BoardValues.EMPTY) {
                System.out.println(" !! SELECTED SQUARE IS FULL !!");
                Thread.sleep(500);
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

        if (xInput < boardSize - 1 && yInput > 0) {
            while (gameBoard[xInput][yInput] == gameBoard[xTemp][yTemp]) {
                count++;
                xTemp++;
                yTemp--;
                if (xTemp == boardSize || yTemp == -1) break;
            }
        }
        xTemp = xInput - 1;
        yTemp = yInput + 1;
        if (xInput > 0 && yInput < boardSize - 1) {
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

        if (xInput > 0 && yInput > 0) {
            while (gameBoard[xInput][yInput] == gameBoard[xTemp][yTemp]) {
                count++;
                xTemp--;
                yTemp--;
                if (xTemp == -1 || yTemp == -1) break;
            }
        }
        xTemp = xInput + 1;
        yTemp = yInput + 1;
        if (xInput < boardSize - 1 && yInput < boardSize - 1) {
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


