package ecole.gestion.vue;

import ecole.metier.Classe;
import ecole.metier.Infos;
import static java.lang.Integer.parseInt;
import methods.Controle;

import java.util.*;

/**
 *
 * @author fabia
 */
public class VueClasse {

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
            String chs = getMsg("1. Ajout\n2. afficher les classes\n3. Rechercher\n4. Modifier\n5. Supprimer\n6. atribuer des classes\n7. Quitter le menu\nVotre choix : ");

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 7) {
                return ch;
            }
            displayMsg("choix invalide");
        } while (true);
    }

    public Classe create() {
        Classe cl;

        String sigle;
        String annee;
        String specialite;
        String nbrEleves;

        do {
            sigle = getMsg("Entrer le sigle (exemple : Cl1)");
        } while (!Controle.verifId(sigle, "Cl"));

        do{
            specialite = getMsg("Entrer la spécialité");
        }while(!Controle.verifAlphabet(specialite));

        do {
            annee = getMsg("Entrer l'année");
        } while (!Controle.verifAnnee(annee));
        int a = parseInt(annee);

        do {
            nbrEleves = getMsg("Entrer le nombre d'élèves");
        } while (!Controle.verifNbrEleve(nbrEleves));
        int nbr = parseInt(nbrEleves);

        cl = new Classe(sigle, a, specialite, nbr);

        return cl;
    }

    public String read() {
        String ns = getMsg("sigle de la classe");
        return ns;
    }

    public void display(Classe cl) {
        displayMsg(cl.toString());
        if (!cl.getListeInfos().isEmpty()) {
            String rep;
            do {
                rep = getMsg("Afficher ses Info (o/n) ");
            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));
            if (rep.equalsIgnoreCase("o")) {
                for (Infos in : cl.getListeInfos()) {
                    displayMsg(in.toString());
                }
            }
        } else {
            System.out.println("pas d'infos");
        }

    }

    public Classe update(Classe cl) {
        int choix;
        String nbrEleves;
        do {
            choix = Integer.parseInt(getMsg("1. Changement du nombre d'élèves\n2. Fin"));

            switch (choix) {
                case 1:
                    do {
                        nbrEleves = getMsg("Entrer le nombre d'élèves");
                    } while (!Controle.verifNbrEleve(nbrEleves));
                    int nbr = parseInt(nbrEleves);

                    cl.setNbrEleves(nbr);
                    break;
                case 2:
                    return cl;
                default:
                    displayMsg("Entrer un nombre entre 1 et 2");
            }
        } while (true);

    }

    public void affAll(Set<Classe> listeClasse) {
        int i = 1;
        for (Classe cl : listeClasse) {
            displayMsg(i + "." + cl.toString());
            i++;
        }
    }
}
