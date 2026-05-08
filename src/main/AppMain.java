package main;

import java.util.Scanner;
import main.menu.Menu;

public class AppMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choixMode = Menu.menuPrincipal(scanner);
        System.out.println();

        Game game = new Game();

        switch (choixMode) {
            case 1:
                System.out.println("        >> Humain vs Humain\n");
                game.runHumainVsHumain();
                break;
            case 2:
                int niveau = Menu.menuNiveau(scanner);
                System.out.println("        >> Humain (WHITE) vs IA niveau " + niveau + " (BLACK)\n");
                game.runHumainVsAI(niveau);
                break;
            case 3:
                int niveauBlanc = Menu.menuNiveauPour(scanner, "WHITE");
                int niveauNoir  = Menu.menuNiveauPour(scanner, "BLACK");
                System.out.println("        >> IA niveau " + niveauBlanc + " (WHITE) vs IA niveau " + niveauNoir + " (BLACK)\n");
                game.runAIvsAI(niveauBlanc, niveauNoir);
                break;
            case 0:
                System.out.println("        A bientot !");
                break;
            default:
                System.out.println("        Choix invalide. Relancez le programme.");
                break;
        }

        scanner.close();
    }
}
