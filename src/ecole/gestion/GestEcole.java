package ecole.gestion;

import ecole.gestion.presenter.*;
import ecole.gestion.modele.*;
import ecole.gestion.vue.*;

import java.util.*;

/**
 *
 * @author fabian
 */
public class GestEcole {

    PresenterEnseignant pe;
    PresenterCours pc;
    PresenterClasse pcl;
    PresenterSalle ps;
    Scanner sc = new Scanner(System.in);

    public void gestion(int mode) {
        DAOClasse mdcl = null;
        DAOCours mdc = null;
        DAOEnseignant mde = null;
        DAOSalle mds = null;
        switch (mode) {
            case 1:
                mdcl = new ModeleClasse();
                mdc = new ModeleCours();
                mde = new ModeleEnseignant();
                mds = new ModeleSalle();
                break;
            case 2:
                
                mdcl = new ModeleClasseDB();
                mdc = new ModeleCoursDB();
                mde = new ModeleEnseignantDB();
                mds = new ModeleSalleDB();
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("mode incorrect");
        }

        VueEnseignant vues = new VueEnseignant();
        pe = new PresenterEnseignant(mde, vues);
        VueCours vuec = new VueCours();
        pc = new PresenterCours(mdc, vuec);
        VueSalle vuesl = new VueSalle();
        ps = new PresenterSalle(mds, vuesl);
        VueClasse vuecl = new VueClasse();
        pcl = new PresenterClasse(mdcl, vuecl, ps, pc, pe);

        do {
            System.out.println("1.Enseignant\n2.Classes\n3.Cours\n4.Salle\n5.fin");
            String ch = sc.nextLine();
            switch (ch) {
                case "1":
                    pe.gestion();
                    break;
                case "2":
                    pcl.gestion();
                    break;
                case "3":
                    pc.gestion();
                    break;
                case "4":
                    ps.gestion();
                    break;
                case "5":
                    System.exit(0);
                default:
                    System.out.println("choix invalide");
            }
        } while (true);
    }

    public static void main(String[] args) {
        int mode = 0;
        do {
            System.out.println("1.Modèle collection\n2.Modèle DB\n3.Quitter\n");
            Scanner sc = new Scanner(System.in);
            try {
                mode = sc.nextInt();
            } catch (Exception e) {
                System.out.println("nombre incorrect");
            }
        } while (mode < 1 || mode > 3);
        GestEcole ge = new GestEcole();
        ge.gestion(mode);
    }

}
