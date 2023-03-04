import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private static Scanner scan = new Scanner(System.in);
    private static Random rand = new Random();
    private static int milliSecTimeout = 700;

    /**
     * Clears the screan :)
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public static void CLS() throws IOException, InterruptedException {
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * It's self explanatory..
     * 
     * @param text Input text
     */
    public static void slowPrint(String text, int speed) {
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            System.out.print(chars[i]);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                // Should not happen
            }
        }
    }

    /**
     * Main Engine for player opponent.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public static void pvP() throws IOException, InterruptedException {
        // Scanner scan = new Scanner(System.in);
        String board[] = { "----", "----", "----", "----" };
        int scoreToWin = 3;
        ArrayList<Integer> xList = new ArrayList<Integer>();
        ArrayList<Integer> yList = new ArrayList<Integer>();

        for (int j = 0; j < board.length; j++) {
            xList.add(j);
            yList.add(j);
        }

        for (int i = 0; i < 3; i++) {
            int temp = rand.nextInt(xList.size());
            int height = xList.get(temp);
            xList.remove(temp);
            temp = rand.nextInt(yList.size());
            int length = yList.get(temp);
            yList.remove(temp);

            if ((board[height].charAt(length)) == '#') {
                i--;
            } else {
                board[height] = (board[height].substring(0, length) + '#' +
                        board[height].substring(length + 1));
            }
        }

        int playerTurn = 1, turnRemaining = 13;
        while (turnRemaining != 0) {
            GameEngine.CLS();
            GameEngine.printBoard(board);

            if (playerTurn == 1) {
                System.out.println("Player 1 [ O ]");
            } else
                System.out.println("Player 2 [ X ]");

            System.out.print("\n Y axes: ");
            String input = scan.next();

            int yInput;
            if (chechInput(input) == 'B') {
                break;
            } else if (chechInput(input) == 'X')
                continue;
            else
                yInput = chechInput(input) - '0';

            System.out.print(" X axes: ");
            input = scan.next();

            int xInput;
            if (chechInput(input) == 'B') {
                break;
            } else if (chechInput(input) == 'X')
                continue;
            else
                xInput = chechInput(input) - '0';

            // Makes input start from 0 instead of user-friendly 1
            xInput--;
            yInput--;

            if (board[yInput].charAt(xInput) != '-') {
                System.out.print("Selected square is used already !!");
                TimeUnit.MILLISECONDS.sleep(milliSecTimeout);
                continue;
            } else {
                switch (playerTurn) {
                    case 1:
                        board[yInput] = (board[yInput].substring(0, xInput) + 'O'
                                + board[yInput].substring(xInput + 1));
                        playerTurn = 2;
                        turnRemaining--;
                        break;
                    case 2:
                        board[yInput] = (board[yInput].substring(0, xInput) + 'X'
                                + board[yInput].substring(xInput + 1));
                        playerTurn = 1;
                        turnRemaining--;
                        break;
                }

                if (GameEngine.winLogic(board, yInput, xInput, scoreToWin)) {
                    turnRemaining++;
                    TimeUnit.SECONDS.sleep(2);
                    break;
                }
            }
        }
        if (turnRemaining == 0) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("Game is a Draw !!", 100);
            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * Main Engine for AI opponent.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public static void pvAi() throws IOException, InterruptedException {
        // Scanner scan = new Scanner(System.in);
        String board[] = { "----", "----", "----", "----" };

        ArrayList<Integer> squarList = new ArrayList<Integer>();

        for (int j = 1; j < 17; j++) {
            squarList.add(j);
        }

        for (int i = 0; i < 3; i++) {
            int temp = rand.nextInt(squarList.size());
            int selectedSquareNum = squarList.get(temp);
            squarList.remove(temp);

            int height = 0;
            while (selectedSquareNum > 4) {
                height++;
                selectedSquareNum -= 4;
            }
            selectedSquareNum--;
            board[height] = (board[height].substring(0, selectedSquareNum) + '#'
                    + board[height].substring(selectedSquareNum + 1));
        }

        int turnRemaining = 13, playerTurn = 1;
        boolean endGame = false;
        int yInput, xInput;
        while ((turnRemaining != 0) && !endGame) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            switch (playerTurn) {
                case 1:
                    System.out.println("Player 1 ( O )");
                    System.out.print(" Y axes: ");

                    String input = scan.next();
                    if (chechInput(input) == 'B') {
                        endGame = true;
                        break;
                    } else if (chechInput(input) == 'X')
                    continue;
                    else
                        yInput = chechInput(input) - '0';

                        System.out.print(" X axes: ");
                        input = scan.next();
                        
                        if (chechInput(input) == 'B') {
                        endGame = true;
                        break;
                    } else if (chechInput(input) == 'X')
                        continue;
                    else
                        xInput = chechInput(input) - '0';

                    // Makes input start from 0 instead of user-friendly 1
                    xInput--;
                    yInput--;

                    if (board[yInput].charAt(xInput) != '-') {
                        System.out.print("Selected square is used already !!");
                        TimeUnit.MILLISECONDS.sleep(700);
                        continue;
                    } else {
                        board[yInput] = (board[yInput].substring(0, xInput) + 'O'
                                + board[yInput].substring(xInput + 1));

                        squarList.remove(squarList.indexOf((yInput * 4 + xInput) + 1));

                        if (GameEngine.winLogic(board, yInput, xInput, 3)) {
                            endGame = true;
                            TimeUnit.SECONDS.sleep(2);
                            turnRemaining++;
                            break;
                        }
                    }
                    playerTurn = 2;
                    turnRemaining--;
                    break;

                case 2:
                    slowPrint("Computer is thinking...", 50);
                    TimeUnit.MILLISECONDS.sleep(milliSecTimeout);

                    int temp = rand.nextInt(squarList.size());
                    int selectedSquareNum = squarList.get(temp);
                    squarList.remove(temp);

                    int height = 0;
                    while (selectedSquareNum > 4) {
                        height++;
                        selectedSquareNum -= 4;
                    }
                    selectedSquareNum--;
                    board[height] = (board[height].substring(0, selectedSquareNum) + 'X'
                            + board[height].substring(selectedSquareNum + 1));

                    if (GameEngine.winLogic(board, height, selectedSquareNum, 3)) {
                        endGame = true;
                        TimeUnit.MILLISECONDS.sleep(milliSecTimeout);
                        turnRemaining++;
                        break;
                    }

                    playerTurn = 1;
                    turnRemaining--;
                    break;
            }
        }

        GameEngine.CLS();
        GameEngine.printBoard(board);
        if (turnRemaining == 0) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("Game is a Draw !!", 100);
            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * Checks for invalid inputs
     * 
     * @param inputLine User input line
     * @return 'X' if input was wrong, a digit (char) if input was ok and 'B' if we
     *         wanted to go back
     */
    public static char chechInput(String inputLine) throws InterruptedException {
        if ((inputLine.charAt(0) <= '9' && inputLine.charAt(0) > '0') && inputLine.length() == 1) {
            return inputLine.charAt(0);
        } else if (inputLine.charAt(0) == '0') {
            return 'B';
        } else {
            System.out.print(" !! Wrong Input !! ");
            TimeUnit.MILLISECONDS.sleep(milliSecTimeout);
            return 'X';
        }
    }

    /**
     * Prints the board :)
     * 
     * @param board 4x4 GameBoard
     */
    private static void printBoard(String board[]) {
        System.out.println("    1   2   3   4");
        for (int i = 0; i < 4; i++) {
            System.out.printf("%d |", i + 1);
            for (int j = 0; j < 4; j++) {
                System.out.printf(" %c |", board[i].charAt(j));
            }
            System.out.println("\n  -----------------");
        }
        System.out.println("\n 0 - Main Menu\n");
    }

    /**
     * Main Logic system for winning and prints a winning massage if they have won
     * 
     * @param board      4x4 Game board
     * @param yInput     Y position of input
     * @param xInput     X position of input
     * @param scoreToWin Minimal score requiered to win
     * @throws IOException
     * @throws InterruptedException
     * @return A boolean to indicate if a player has won
     */
    private static boolean winLogic(String board[], int yInput, int xInput, int scoreToWin)
            throws IOException, InterruptedException {

        if (GameEngine.upDownCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match! ", 100);
            return true;
        } else if (GameEngine.leftRightCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match! ", 100);
            return true;
        } else if (GameEngine.topRightDiagonalCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match! ", 100);
            return true;
        } else if (GameEngine.topLeftDiagonalCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match! ", 100);
            return true;
        }
        return false;
    }

    /////////////////////////////////////////// COUNTERS

    /**
     * @param board 4x4 GameBoard
     * @param yAxes Y position of input
     * @param xAxes X position of input
     * @return Count of a similar sign in Diagonal (Right to left) line
     */
    private static int upDownCounter(String board[], int yAxes, int xAxes) {
        int count = 1;

        if (yAxes != 0) {
            int temp = yAxes - 1;
            while (board[temp].charAt(xAxes) == board[yAxes].charAt(xAxes)) {
                count++;
                temp--;
                if (temp == -1)
                    break;
            }
        }
        if (yAxes != 3) {
            int temp = yAxes + 1;
            temp = yAxes + 1;
            while (board[temp].charAt(xAxes) == board[yAxes].charAt(xAxes)) {
                count++;
                temp++;
                if (temp == 4)
                    break;
            }
        }
        return count;
    }

    /**
     * Align counter for left and right direction.
     * 
     * @param board 4x4 GameBoard
     * @param yAxes Y position of input
     * @param xAxes X position of input
     * @return Count of a similar sign in horizontal line
     */
    private static int leftRightCounter(String board[], int yAxes, int xAxes) {
        int count = 1;

        if (xAxes != 0) {
            int temp = xAxes - 1;
            while (board[yAxes].charAt(temp) == board[yAxes].charAt(xAxes)) {
                count++;
                temp--;
                if (temp == -1)
                    break;
            }
        }
        if (xAxes != 3) {
            int temp = xAxes + 1;
            while (board[yAxes].charAt(temp) == board[yAxes].charAt(xAxes)) {
                count++;
                temp++;
                if (temp == 4)
                    break;
            }
        }
        return count;
    }

    /**
     * @param board 4x4 GameBoard
     * @param yAxes Y position of input
     * @param xAxes X position of input
     * @return Count of a similar sign in Vertical line
     */
    private static int topRightDiagonalCounter(String board[], int yAxes, int xAxes) {
        int count = 1;

        if (xAxes != 3 && yAxes != 0) {
            int tempX = xAxes + 1;
            int tempY = yAxes - 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX++;
                tempY--;

                if (tempX == 4 || tempY == -1)
                    break;
            }
        }
        if (xAxes != 0 && yAxes != 3) {
            int tempX = xAxes - 1;
            int tempY = yAxes + 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX--;
                tempY++;

                if (tempX == -1 || tempY == 4)
                    break;
            }
        }
        return count;
    }

    /**
     * @param board 4x4 GameBoard
     * @param yAxes Y position of input
     * @param xAxes X position of input
     * @return Count of same in Diagonal (Left to Right) line
     */
    private static int topLeftDiagonalCounter(String board[], int yAxes, int xAxes) {
        int count = 1;

        if (xAxes != 0 && yAxes != 0) {
            int tempX = xAxes - 1;
            int tempY = yAxes - 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX--;
                tempY--;

                if (tempX == -1 || tempY == -1)
                    break;
            }
        }
        if (xAxes != 3 && yAxes != 3) {
            int tempX = xAxes + 1;
            int tempY = yAxes + 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX++;
                tempY++;

                if (tempX == 4 || tempY == 4)
                    break;
            }
        }
        return count;
    }
}
