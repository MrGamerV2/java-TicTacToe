import java.util.Scanner;

/**
 * @author Ilya Mokhtarabadi
 */
public class Game {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int exitCode = 0;

        while (exitCode == 0) {
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
            
        }
        scan.close();
    }
}
