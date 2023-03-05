import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private static Scanner scan = new Scanner(System.in);
    private static Random rand = new Random();
    private static int milliSecTimeout = 500;

    // SETTING VALUES READ FROM FILE
    private static int boardSize = 5;
    private static int scoreToWin = 3;
    private static int nLockedSquares = 10;

    /**
     * Clears the screan :)
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public static void CLS() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // System.out.print("\033[H\033[2J");
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
        // Board Creation
        String[] board = new String[boardSize];

        for (int i = 0; i < boardSize; i++) {
            board[i] = "";
            for (int j = 0; j < board.length; j++) {
                board[i] += '-';
            }
        }

        ArrayList<Integer> squarList = new ArrayList<Integer>();
        for (int j = 1; j < (boardSize * boardSize + 1); j++) {
            squarList.add(j);
        }

        for (int i = 0; i < nLockedSquares; i++) {
            int temp = rand.nextInt(squarList.size());
            int selectedSquareNum = squarList.get(temp);
            squarList.remove(temp);

            int height = 0;
            while (selectedSquareNum > boardSize) {
                height++;
                selectedSquareNum -= boardSize;
            }
            selectedSquareNum--;
            board[height] = (board[height].substring(0, selectedSquareNum) + '#'
                    + board[height].substring(selectedSquareNum + 1));
        }
        squarList.clear();

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
            if (checkInput(input) == 'X') {
                continue;
            } else if (checkInput(input) == 'B')
                break;
            else
                yInput = checkInput(input) - '0';

            System.out.print(" X axes: ");
            input = scan.next();

            int xInput;
            if (checkInput(input) == 'X') {
                continue;
            } else if (checkInput(input) == 'B')
                break;
            else
                xInput = checkInput(input) - '0';

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
        // Board Creation
        String[] board = new String[boardSize];

        for (int i = 0; i < boardSize; i++) {
            board[i] = "";
            for (int j = 0; j < board.length; j++) {
                board[i] += '-';
            }
        }

        ArrayList<Integer> squarList = new ArrayList<Integer>();
        for (int j = 1; j < (boardSize * boardSize) + 1; j++) {
            squarList.add(j);
        }

        for (int i = 0; i < nLockedSquares; i++) {
            int temp = rand.nextInt(squarList.size());
            int selectedSquareNum = squarList.get(temp);
            squarList.remove(temp);

            int height = 0;
            while (selectedSquareNum > boardSize) {
                height++;
                selectedSquareNum -= boardSize;
            }
            selectedSquareNum--;
            board[height] = (board[height].substring(0, selectedSquareNum) + '#'
                    + board[height].substring(selectedSquareNum + 1));
        }

        int turnRemaining = (boardSize * boardSize) - nLockedSquares, playerTurn = 1;
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
                    if (checkInput(input) == 'X')
                        continue;
                    else if (checkInput(input) == 'B') {
                        endGame = true;
                        break;
                    } else
                        yInput = checkInput(input) - '0';

                    System.out.print(" X axes: ");
                    input = scan.next();

                    if (checkInput(input) == 'X')
                        continue;
                    else if (checkInput(input) == 'B') {
                        endGame = true;
                        break;
                    } else
                        xInput = checkInput(input) - '0';

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

                        if (GameEngine.winLogic(board, yInput, xInput, scoreToWin)) {
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
                    slowPrint("Computer is thinking...", 40);
                    TimeUnit.MILLISECONDS.sleep(milliSecTimeout);

                    int temp = rand.nextInt(squarList.size());
                    int selectedSquareNum = squarList.get(temp);
                    squarList.remove(temp);

                    int height = 0;
                    while (selectedSquareNum > boardSize) {
                        height++;
                        selectedSquareNum -= boardSize;
                    }
                    selectedSquareNum--;
                    board[height] = (board[height].substring(0, selectedSquareNum) + 'X'
                            + board[height].substring(selectedSquareNum + 1));

                    if (GameEngine.winLogic(board, height, selectedSquareNum, scoreToWin)) {
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
    public static char checkInput(String inputLine) throws InterruptedException {
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
     * @param board X square Gameboard
     */
    private static void printBoard(String board[]) {
        String temp = " ";

        for (int i = 0; i < boardSize; i++) {
            temp += "   " + (i + 1);
        }
        System.out.println(temp);
        temp = "\n  ";
        for (int i = 0; i < boardSize; i++) {
            temp += "----";
        }

        for (int i = 0; i < boardSize; i++) {
            System.out.printf("%d |", i + 1);
            for (int j = 0; j < boardSize; j++) {
                System.out.printf(" %c |", board[i].charAt(j));
            }
            System.out.println(temp + '-');
        }
        System.out.println("\n 0 - Main Menu\n");
    }

    /**
     * Main Logic system for winning and prints a winning massage if they have won
     * 
     * @param board      X square Gameboard
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
                    " ] has won this match!!! ", 100);
            return true;
        } else if (GameEngine.leftRightCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match!!! ", 100);
            return true;
        } else if (GameEngine.topRightDiagonalCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match!!! ", 100);
            return true;
        } else if (GameEngine.topLeftDiagonalCounter(board, yInput, xInput) >= scoreToWin) {
            GameEngine.CLS();
            GameEngine.printBoard(board);
            slowPrint("\n[ " + board[yInput].charAt(xInput) +
                    " ] has won this match!!! ", 100);
            return true;
        }
        return false;
    }

    /////////////////////////////////////////// COUNTERS

    /**
     * @param board X square Gameboard
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
        if (yAxes != (boardSize - 1)) {
            int temp = yAxes + 1;
            temp = yAxes + 1;
            while (board[temp].charAt(xAxes) == board[yAxes].charAt(xAxes)) {
                count++;
                temp++;
                if (temp == boardSize)
                    break;
            }
        }
        return count;
    }

    /**
     * Align counter for left and right direction.
     * 
     * @param board X square Gameboard
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
        if (xAxes != (boardSize - 1)) {
            int temp = xAxes + 1;
            while (board[yAxes].charAt(temp) == board[yAxes].charAt(xAxes)) {
                count++;
                temp++;
                if (temp == boardSize)
                    break;
            }
        }
        return count;
    }

    /**
     * @param board X square Gameboard
     * @param yAxes Y position of input
     * @param xAxes X position of input
     * @return Count of a similar sign in Vertical line
     */
    private static int topRightDiagonalCounter(String board[], int yAxes, int xAxes) {
        int count = 1;

        if (xAxes != (boardSize - 1) && yAxes != 0) {
            int tempX = xAxes + 1;
            int tempY = yAxes - 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX++;
                tempY--;

                if (tempX == boardSize || tempY == -1)
                    break;
            }
        }
        if (xAxes != 0 && yAxes != (boardSize - 1)) {
            int tempX = xAxes - 1;
            int tempY = yAxes + 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX--;
                tempY++;

                if (tempX == -1 || tempY == boardSize)
                    break;
            }
        }
        return count;
    }

    /**
     * @param board X square Gameboard
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
        if (xAxes != (boardSize - 1) && yAxes != (boardSize - 1)) {
            int tempX = xAxes + 1;
            int tempY = yAxes + 1;

            while (board[tempY].charAt(tempX) == board[yAxes].charAt(xAxes)) {
                count++;
                tempX++;
                tempY++;

                if (tempX == boardSize || tempY == boardSize)
                    break;
            }
        }
        return count;
    }
}