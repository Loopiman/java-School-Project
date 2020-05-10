package ecole.gestion.vue;

import java.util.*;
import ecole.metier.Salle;
import static java.lang.Integer.parseInt;
import methods.Controle;

/**
 *
 * @author fabian
 */
public class VueSalle {

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
            String chs = getMsg("1. Ajout\n2. Afficher les salles\n3. Rechercher\n4. Modifier\n5. Supprimer\n6. Quitter le menu\n\nVotre choix : ");

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 6) {
                return ch;
            }
            displayMsg("choix invalide");
        } while (true);
    }

    public Salle create() {
        Salle s;

        String sigle;
        String capacite;

        do {
            sigle = getMsg("Entrer le sigle (exemple : S1)");
        } while (!Controle.verifId(sigle, "S"));

        do {
            capacite = getMsg("Entrer la capacité");
        } while (!Controle.verifNbrEleve(capacite));
        int nbr = parseInt(capacite);

        s = new Salle(sigle, nbr);

        return s;
    }

    public String read() {
        String ns = getMsg("sigle de la salle");
        return ns;
    }

    public void display(Salle s) {
        displayMsg(s.toString());
    }

    public Salle update(Salle s) {
        int choix;
        String capacite;
        do {
            choix = Integer.parseInt(getMsg("1. Changement de la capacité\n2. Fin"));

            switch (choix) {
                case 1:

                    do {
                        capacite = getMsg("Entrer la capacité");
                    } while (!Controle.verifNbrEleve(capacite));
                    int nbr = parseInt(capacite);
                    s.setCapacite(nbr);
                    break;
                case 2:
                    return s;
                default:
                    displayMsg("Entrer un nombre entre 1 et 2");
            }
        } while (true);

    }

    public void affAll(Set<Salle> ls) {
        int i = 1;
        for (Salle s : ls) {
            displayMsg(i + "." + s.toString());
            i++;
        }
    }
}
