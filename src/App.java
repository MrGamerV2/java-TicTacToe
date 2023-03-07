public class App {
    public static void main(String[] args) {
        GameBoard board = new GameBoard();

        board.setBoardSize(4);
        board.createGameBoard(3);
        clearScreen();
        board.printBoard(true);
    }

    public static void clearScreen(){
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
