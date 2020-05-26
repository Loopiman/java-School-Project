package ecole.gestion.vue;

import ecole.metier.*;

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
        String chs;
        do {
            do {
                chs = getMsg("1. Ajout\n2. Afficher les enseignants\n3. Rechercher\n4. Modifier\n5. Supprimer\n6. Quitter le menu\n\nVotre choix : ");
            } while (!Controle.verifInteger(chs));

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 6) {
                return ch;
            }
            displayMsg("choix invalide");
        } while (true);
    }

    public int menuTri() {
        int ch;
        String chs;
        do {
            do {
                chs = getMsg("Tri par :\n1. Matricule\n2. Nom\n3. Date engagement\n\nVotre choix : ");
            } while (!Controle.verifInteger(chs));

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 3) {
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
            matricule = getMsg("Entrer le matricule (exemple : E1)");
        } while (Controle.verifId(matricule, "E") == false);

        do {
            nom = getMsg("Entrer le nom : ");
        } while (!Controle.verifAlphabet(nom));

        do {
            prenom = getMsg("Entrer le prénom : ");
        } while (!Controle.verifAlphabet(prenom));

        do {
            tel = getMsg("Entrer le telephone sous le format 04xx/xx.xx.xx :");
        } while (!Controle.verifNumero(tel));

        do {
            chargeSem = getMsg("Entrer le nombre d'heures par semaine : ");
        } while (!Controle.verifChargesem(chargeSem));
        int charge = Integer.parseInt(chargeSem);

        int chargeRest = charge;

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

        e = new Enseignant(matricule, nom, prenom, tel, charge, chargeRest, salaire, dateEngagement);
        return e;
    }

    public String read() {
        String ns = getMsg("matricule de l'enseignant");
        return ns;
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

    public void affAll(Set<Enseignant> le) {
        int i = 1;

        for (Enseignant e : le) {
            displayMsg(i + "." + e.toString());
            i++;
        }
    }

    // https://www.javatpoint.com/java-lambda-expressions
    public void affAll(Set<Enseignant> le, int mode) {
        int i = 1;
        List<Enseignant> tri = new ArrayList<>();
        for (Enseignant e : le) {
            tri.add(e);
        }
        switch (mode) {
            case 1:
                Collections.sort(tri, (p1, p2) -> {
                    return p1.getMatricule().compareTo(p2.getMatricule());
                });
                break;
            case 2:
                Collections.sort(tri, (p1, p2) -> {
                    return p1.getNom().compareTo(p2.getNom());
                });
                break;
            case 3:
                Collections.sort(tri, (p1, p2) -> {
                    return p1.getDateEngagement().compareTo(p2.getDateEngagement());
                });
                break;
        }

        for (Enseignant e : tri) {
            displayMsg(i + "." + e.toString());
            i++;
        }
    }

    private BigDecimal setParseBigDecimal(String salaireMensu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
