package main.menu;

import java.util.Scanner;

public class Menu {
    
    // ─── Menu principal ───────────────────────────────────────────────────────

    public static int menuPrincipal(Scanner scanner) {
        System.out.println();
        System.out.println("        LA PERCEE  --  Jeu strategique 8x8");
        System.out.println("        ─────────────────────────────────────");
        System.out.println("        1.  Humain  vs  Humain");
        System.out.println("        2.  Humain  vs  IA");
        System.out.println("        3.  IA      vs  IA");
        System.out.println("        0.  Quitter");
        System.out.println("        ─────────────────────────────────────");
        System.out.println();
        System.out.print("        Votre choix : ");
        return lireEntier(scanner);
    }

    // ─── Menu niveau (générique) ──────────────────────────────────────────────

    public static int menuNiveau(Scanner scanner) {
        System.out.println();
        System.out.println("        Niveau de difficulte de l'IA");
        System.out.println("        ─────────────────────────────────────");
        System.out.println("        1.  Facile       (evaluation materielle,     profondeur 2)");
        System.out.println("        2.  Intermediaire(evaluation positionnelle,  profondeur 4)");
        System.out.println("        3.  Difficile    (evaluation strategique,    profondeur 6)");
        System.out.println("        ─────────────────────────────────────");
        System.out.println();
        System.out.print("        Votre choix : ");
        int choix = lireEntier(scanner);
        return (choix >= 1 && choix <= 3) ? choix : 1;
    }

    // ─── Menu niveau pour un joueur specifique (mode IA vs IA) ───────────────

    public static int menuNiveauPour(Scanner scanner, String joueur) {
        System.out.println();
        System.out.println("        Niveau de difficulte pour " + joueur);
        System.out.println("        ─────────────────────────────────────");
        System.out.println("        1.  Facile");
        System.out.println("        2.  Intermediaire");
        System.out.println("        3.  Difficile");
        System.out.println("        ─────────────────────────────────────");
        System.out.println();
        System.out.print("        Votre choix : ");
        int choix = lireEntier(scanner);
        return (choix >= 1 && choix <= 3) ? choix : 1;
    }

    // ─── Utilitaire ──────────────────────────────────────────────────────────

    public static int lireEntier(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("        Entree invalide.");
            return -1;
        }
    }
    
}
