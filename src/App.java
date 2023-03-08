import java.util.Random;
import java.util.Scanner;

public class App {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        // SETTINGS
        int boardSize = 4;
        int nLockedSquares = 3;
        int alignNtoWin = 3;

        if (nLockedSquares >= boardSize * boardSize) {
            System.out.println(" CHECK SETTINGS | LOCKED SQUARES > BOARD SIZE");
            System.exit(1);
        }
        Player p1 = new Player(true); // X
        Player p2 = new Player(false); // O

        boolean noExit = true;
        while (noExit) {
            clearScreen();
            System.out.print("""
                                     ------------------------------------------------------------------------------------
                                     ######## ####  ######     ########    ###     ######     ########  #######  ########
                                        ##     ##  ##    ##       ##      ## ##   ##    ##       ##    ##     ## ##
                                        ##     ##  ##             ##     ##   ##  ##             ##    ##     ## ##
                                        ##     ##  ##             ##    ##     ## ##             ##    ##     ## ######
                                        ##     ##  ##             ##    ######### ##             ##    ##     ## ##
                                        ##     ##  ##    ##       ##    ##     ## ##    ##       ##    ##     ## ##
                                        ##    ####  ######        ##    ##     ##  ######        ##     #######  ########
                                     ------------------------------------------------------------------------------------
                                                            ||    Choose Your Opponent    ||
                                         1 - Player

                                         2 - Computer(a dum dum)

                                         0 - Exit

                                            >>""" + " ");
            int option;
            try {
                option = Integer.parseInt(scan.next());
            } catch (NumberFormatException e) {
                System.out.println(" !! Wrong Input Detected !!");
                Thread.sleep(500);
                continue;
            }
            switch (option) {
                case 1 -> playerVersusPlayer(p1, p2, boardSize, nLockedSquares, alignNtoWin);
                case 2 -> playerVersusAI(p1, p2, boardSize, nLockedSquares, alignNtoWin);
                case 0 -> {
                    noExit = false;
                    scan.close();
                }
                default -> {
                    System.out.println(" !! Input is out of context !! ");
                    Thread.sleep(500);
                }
            }
        }
    }

    /**
     * Clears Screen ノ( º _ ºノ)
     */
    public static void clearScreen() {
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Player versus Player mode
     * @param p1 Player 1 [ O ]
     * @param p2 Player 2 [ X ]
     * @param boardSize Size of the game-board
     * @param nLockedSquares Amount of locked squares in board
     * @param alignNtoWin Amount of squares needed to be aligned to win
     * @throws InterruptedException I have no idea what this does ¯\_(ツ)_/¯
     */
    public static void playerVersusPlayer(Player p1, Player p2, int boardSize, int nLockedSquares, int alignNtoWin) throws InterruptedException {
        Board board = new Board();
        board.setBoardSize(boardSize);
        board.createGameBoard(nLockedSquares);

        boolean isPlayer1Turn = true;
        int remainingTurns = boardSize * boardSize - nLockedSquares;
        while (remainingTurns != 0) {
            clearScreen();
            board.printBoard();
            if (isPlayer1Turn) {
                if (p1.getInput() && p1.fillCell(board.gameBoard, boardSize)) {
                    remainingTurns--;
                    isPlayer1Turn = false;
                    if (p1.winLogic(board.gameBoard, boardSize, alignNtoWin)) {
                        clearScreen();
                        board.printBoard();
                        System.out.println(" || [ X ] HAS WON THIS MATCH ||");
                        Thread.sleep(3000);
                        break;
                    }
                }
            } else {
                if (p2.getInput() && p2.fillCell(board.gameBoard, boardSize)) {
                    remainingTurns--;
                    isPlayer1Turn = true;
                    if (p2.winLogic(board.gameBoard, boardSize, alignNtoWin)) {
                        clearScreen();
                        board.printBoard();
                        System.out.println(" || [ O ] HAS WON THIS MATCH ||");
                        Thread.sleep(3000);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Player versus AI mode
     * @param p1 Player 1 [ O ]
     * @param p2 Player 2 [ X ]
     * @param boardSize Size of the game-board
     * @param nLockedSquares Amount of locked squares in board
     * @param alignNtoWin Amount of squares needed to be aligned to win
     * @throws InterruptedException Again; I have no idea what this does ¯\_(ツ)_/¯
     */
    public static void playerVersusAI(Player p1, Player p2, int boardSize, int nLockedSquares, int alignNtoWin) throws InterruptedException {
        Board board = new Board();
        board.setBoardSize(boardSize);
        board.createGameBoard(nLockedSquares);


        boolean isPlayer1Turn = true;
        int remainingTurns = boardSize * boardSize - nLockedSquares;
        while (remainingTurns != 0) {
            clearScreen();
            board.printBoard();
            if (isPlayer1Turn) {
                if (p1.getInput() && p1.fillCell(board.gameBoard, boardSize)) {
                    board.squareList.remove((Integer) (p1.yInput * boardSize + p1.xInput));
                    if (p1.winLogic(board.gameBoard, boardSize, alignNtoWin)) {
                        clearScreen();
                        board.printBoard();
                        System.out.println(" || [ X ] HAS WON THIS MATCH ||");
                        Thread.sleep(3000);
                        break;
                    }
                    remainingTurns--;
                    isPlayer1Turn = false;
                }
            } else {
                Random rand = new Random();
                int tempIndex;
                int tempLoc;
                tempLoc = board.squareList.get(tempIndex = rand.nextInt(board.squareList.size()));
                board.squareList.remove(tempIndex);
                tempIndex = 0; // NOW USING AS yAXES HOLDER
                while (tempLoc > boardSize - 1) {
                    tempLoc -= boardSize;
                    tempIndex++;
                }
                p2.xInput = tempLoc;
                p2.yInput = tempIndex;
                if (p2.fillCell(board.gameBoard, boardSize)) {
                    if (p2.winLogic(board.gameBoard, boardSize, alignNtoWin)) {
                        clearScreen();
                        board.printBoard();
                        System.out.println(" || [ O ] HAS WON THIS MATCH ||");
                        Thread.sleep(3000);
                        break;
                    }
                    remainingTurns--;
                    isPlayer1Turn = true;
                }
            }
        }
    }
}
