import java.io.IOException;
import java.util.Scanner;

/**
 * @author Ilya Mokhtarabadi
 */
public class Game {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        int exitCode = 0;
        GameEngine gm = new GameEngine();

        while (exitCode == 0) {
            GameEngine.clearScreen();
            System.out.print("" +
                    "------------------------------------------------------------------------------------\n" +
                    "######## ####  ######     ########    ###     ######     ########  #######  ########\n" +
                    "   ##     ##  ##    ##       ##      ## ##   ##    ##       ##    ##     ## ##      \n" +
                    "   ##     ##  ##             ##     ##   ##  ##             ##    ##     ## ##      \n" +
                    "   ##     ##  ##             ##    ##     ## ##             ##    ##     ## ######  \n" +
                    "   ##     ##  ##             ##    ######### ##             ##    ##     ## ##      \n" +
                    "   ##     ##  ##    ##       ##    ##     ## ##    ##       ##    ##     ## ##      \n" +
                    "   ##    ####  ######        ##    ##     ##  ######        ##     #######  ########\n" +
                    "------------------------------------------------------------------------------------\n" +
                    "                       ||    Choose Your Opponent    ||\n\n" +
                    "    1 - Player\n\n    2 - Computer(a dum dum)\n\n    0 - Exit\n\n>>  ");

            String input = scan.next();
            int option;
            if (gm.checkInput(input) == 'X') {
                continue;
            } else if (gm.checkInput(input) == 'B') {
                exitCode = 1;
                continue;
            } else {
                option = gm.checkInput(input) - '0';
            }

            switch (option) {
                case 1 -> gm.pvP();
                case 2 -> gm.pvAi();
                case 0 -> exitCode = 1;
            }
        }
        scan.close();
    }
}
