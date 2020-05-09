package ecole.gestion.vue;

import ecole.metier.*;
import static java.lang.Integer.parseInt;
import methods.Controle;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author fabia
 */
public class VueEnseignant {

    private Scanner sc = new Scanner(System.in);

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
            String chs = getMsg("1. Ajout\n2. Rechercher\n3. Modifier\n4. Supprimer\n5. Afficher les enseignants\n6. Quitter le menu\n\nVotre choix : ");

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 6) {
                return ch;
            }
            displayMsg("choix invalide");
        } while (true);
    }

    public Enseignant create() {

        Enseignant e;

        String matricule;
        String nom;
        String prenom;
        String tel;
        String chargeSem;
        String salaireMensu;
        String date;
        LocalDate dateEngagement = null;

        do {
            matricule = getMsg("Entrer le matricule (exemple : E1");
        } while (Controle.verifId(matricule, "E") == false);

        nom = getMsg("Entrer le nom : ");

        prenom = getMsg("Entrer le prénom : ");

        do {
            tel = getMsg("Entrer le telephone :");
        } while (!Controle.verifNumero(tel));

        do {
            chargeSem = getMsg("Entrer le nombre d'heures par semaine : ");
        } while (!Controle.verifChargesem(chargeSem));
        int charge = Integer.parseInt(chargeSem);

        do {
            salaireMensu = getMsg("Entrer le salaire mensuel : ");
        } while (!Controle.verifSalaire(salaireMensu));
        BigDecimal salaire = new BigDecimal(salaireMensu);

        boolean ok;

        do {
            date = getMsg("Entrer la date d'engagement sous le format DD-MM-AAAA: ");
            if (Controle.insertDate(date) != null) {
                dateEngagement = Controle.insertDate(date);
                ok = true;

            } else {
                ok = false;
            }
        } while (ok == false);

        e = new Enseignant(matricule, nom, prenom, tel, charge, salaire, dateEngagement);
        return e;
    }

    public void display(Enseignant e) {
        displayMsg(e.toString());
        if (!e.getListeInfos().isEmpty()) {
            String rep;
            do {
                rep = getMsg("Afficher ses Infos (o/n) ");
            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));
            if (rep.equalsIgnoreCase("o")) {
                for (Infos i : e.getListeInfos()) {
                    displayMsg(i.toString());
                }
            }
        } else {
            System.out.println("pas d'infos");
        }
    }

    public Enseignant update(Enseignant e) {
        int choixUpdate;
        do {
            String ntel;
            String newChargeSem;
            String newSalaire;

            System.out.println("1. Changement de telephone\n2. Changement de la charge\n3. Changement du salaire mensuel\n4. Fin");
            choixUpdate = sc.nextInt();

            switch (choixUpdate) {
                case 1:
                    System.out.println("Nouveau numéro : ");
                    sc.skip("\n");
                    do {
                        ntel = sc.nextLine();
                    } while (!Controle.verifNumero(ntel));
                    e.setTel(ntel);
                    break;
                case 2:
                    sc.skip("\n");
                    do {
                        System.out.println("Nouvelle charge : ");
                        newChargeSem = sc.nextLine();
                    } while (!Controle.verifChargesem(newChargeSem));
                    int ncharge = Integer.parseInt(newChargeSem);

                    e.setChargeSem(ncharge);
                    break;
                case 3:
                    sc.skip("\n");
                    do {
                        System.out.println("Nouveau salaire : ");
                        newSalaire = sc.nextLine();
                    } while (!Controle.verifSalaire(newSalaire));
                    BigDecimal nsal = new BigDecimal(newSalaire);

                    e.setSalaireMensu(nsal);
                    break;
                case 4:
                    sc.skip("\n");
                    return e;

                default:
                    System.out.println("choix invalide");
            }
        } while (true);
    }

    public String read() {
        String ns = getMsg("matricule de l'enseignant");
        return ns;
    }

    public void affAll(List<Enseignant> le) {
        int i = 1;
        for (Enseignant e : le) {
            displayMsg(i + "." + e.toString());
            i++;
        }
    }

    private BigDecimal setParseBigDecimal(String salaireMensu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
