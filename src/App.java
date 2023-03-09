import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {
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

                         3 - Settings

                         0 - Exit

                           >>""" + " ");
            int option = testInput();
            if (Settings.nLockedSquares >= Settings.boardSize * Settings.boardSize) {
                System.out.println(" CHECK SETTINGS | LOCKED SQUARES > BOARD CELLS");
                Thread.sleep(1000);
                option = 3;
            }
            switch (option) {
                case 1 -> playerVersusPlayer(p1, p2);
                case 2 -> playerVersusAI(p1, p2);
                case 3 -> settings();
                case 0 -> noExit = false;
                case -25 -> {
                    System.out.println(" !! WRONG INPUT DETECTED !!");
                    Thread.sleep(700);
                }
                default -> {
                    System.out.println(" !! Input is out of context !! ");
                    Thread.sleep(700);
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
     *
     * @param p1 Player 1 [ O ]
     * @param p2 Player 2 [ X ]
     * @throws InterruptedException I have no idea what this does ¯\_(ツ)_/¯
     */
    public static void playerVersusPlayer(Player p1, Player p2) throws InterruptedException {
        Board board = new Board();
        board.createGameBoard();

        boolean isPlayer1Turn = true;
        int remainingTurns = Settings.boardSize * Settings.boardSize - Settings.nLockedSquares;
        while (remainingTurns != 0) {
            clearScreen();
            board.printBoard();
            if (isPlayer1Turn) {
                if (p1.getInput() && p1.fillCell(board.gameBoard, Settings.boardSize)) {
                    remainingTurns--;
                    isPlayer1Turn = false;
                    if (p1.winLogic(board.gameBoard)) {
                        clearScreen();
                        board.printBoard();
                        System.out.println(" || [ X ] HAS WON THIS MATCH ||");
                        Thread.sleep(3000);
                        break;
                    }
                }
            } else {
                if (p2.getInput() && p2.fillCell(board.gameBoard, Settings.boardSize)) {
                    remainingTurns--;
                    isPlayer1Turn = true;
                    if (p2.winLogic(board.gameBoard)) {
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
     *
     * @param p1 Player 1 [ O ]
     * @param p2 Player 2 [ X ]
     * @throws InterruptedException Again; I have no idea what this does ¯\_(ツ)_/¯
     */
    public static void playerVersusAI(Player p1, Player p2) throws InterruptedException {
        Board board = new Board();
        board.createGameBoard();

        boolean isPlayer1Turn = true;
        int remainingTurns = Settings.boardSize * Settings.boardSize - Settings.nLockedSquares;
        while (remainingTurns != 0) {
            clearScreen();
            board.printBoard();
            if (isPlayer1Turn) {
                if (p1.getInput() && p1.fillCell(board.gameBoard, Settings.boardSize)) {
                    board.squareList.remove((Integer) (p1.yInput * Settings.boardSize + p1.xInput));
                    if (p1.winLogic(board.gameBoard)) {
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
                while (tempLoc > Settings.boardSize - 1) {
                    tempLoc -= Settings.boardSize;
                    tempIndex++;
                }
                p2.xInput = tempLoc;
                p2.yInput = tempIndex;
                if (p2.fillCell(board.gameBoard, Settings.boardSize)) {
                    if (p2.winLogic(board.gameBoard)) {
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

    public static int testInput() {
        Scanner scan = new Scanner(System.in);
        int input;
        try {
            input = Integer.parseInt(scan.next());
        } catch (NumberFormatException e) {
            input = -25;
        }
        return input;
    }

    public static void settings() {
        while (true) {
            clearScreen();
            System.out.println(
                    " Define your new boardSize, Amount of locked cells and amount of aligned cells needed to win.");
            System.out.printf(" What is the board size (currently = %d)? ", Settings.boardSize);
            Settings.boardSize = testInput();
            if (Settings.boardSize != -25) {
                System.out.printf(" How many locked cells (currently = %d)? ", Settings.nLockedSquares);
                Settings.nLockedSquares = testInput();
                if (Settings.nLockedSquares != -25) {
                    System.out.printf(" How many cells need to be aligned (currently = %d)? ", Settings.alignNtoWin);
                    Settings.alignNtoWin = testInput();
                    if (Settings.alignNtoWin != -25)
                        break;
                }
            }
        }
    }
}
