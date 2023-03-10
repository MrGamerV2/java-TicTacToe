import java.io.IOException;
import java.util.Scanner;

/**
 * @author Ilya Mokhtarabadi
 */
public class Game {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        // Scanner scan = new Scanner(System.in);
        int exitCode = 0;

        while (exitCode == 0) {
            GameEngine.CLS();
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
            if (GameEngine.chechInput(input) != 'X') {
                option = GameEngine.chechInput(input) - '0';
            } else {
                continue;
            }

            switch (option) {
                case 1:
                    GameEngine.pvP();
                    break;
                case 2:
                    GameEngine.pvAi();
                    break;
                case 0:
                    exitCode = 1;
                    break;
            }
        }
        scan.close();
    }
}
