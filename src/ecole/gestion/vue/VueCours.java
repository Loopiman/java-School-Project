package ecole.gestion.vue;

import java.util.*;
import ecole.metier.Cours;
import static java.lang.Integer.parseInt;
import methods.Controle;

/**
 *
 * @author fabian
 */
public class VueCours {

    Scanner sc = new Scanner(System.in);

    public void displayMsg(String msg) {
        System.out.println(msg);
    }

    public String getMsg(String invite) {
        displayMsg(invite);
        String msg = sc.nextLine();

        return msg;
    }

    public int menu() {
        int ch;
        do {
            String chs = getMsg("1. Ajout\n2. Afficher les cours\n3. Rechercher\n4. Modifier\n5. Supprimer\n6. Quitter le menu\n\nVotre choix : ");

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 6) {
                return ch;
            }
            displayMsg("choix invalide");
        } while (true);
    }

    public Cours create() {
        Cours c;

        String code;
        String intitule;
        String nhs;

        do {
            code = getMsg("Entrer le code (exemple : C1)");
        } while (!Controle.verifId(code, "C"));

        do{
            intitule = getMsg("Entrer l'intitulé");
        }while(!Controle.verifAlphabet(intitule));

        do {
            nhs = getMsg("Entrer le nombre d'heures par semaine");
        } while (!Controle.verifChargesem(nhs));
        int heureSem = parseInt(nhs);

        c = new Cours(code, intitule, heureSem);

        return c;
    }

    public String read() {
        String ns = getMsg("code du cours");
        return ns;
    }

    public void display(Cours c) {
        displayMsg(c.toString());

    }

    public Cours update(Cours c) {
        int choix;
        String nhs;
        do {
            choix = Integer.parseInt(getMsg("1. Changement du nombre d'heures par semaine\n2. Fin"));

            switch (choix) {
                case 1:
                    do {
                        nhs = getMsg("Entrer le nombre d'heures par semaine");
                    } while (!Controle.verifChargesem(nhs));
                    int heureSem = parseInt(nhs);
                    c.setNhs(heureSem);
                    break;
                case 2:
                    return c;
                default:
                    displayMsg("Entrer un nombre entre 1 et 2");
            }
        } while (true);

    }

    public void affAll(Set<Cours> lc) {
        int i = 1;
        for (Cours c : lc) {
            displayMsg(i + "." + c.toString());
            i++;
        }
    }
}
