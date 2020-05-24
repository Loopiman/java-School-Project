package ecole.gestion;

import ecole.gestion.presenter.*;
import ecole.gestion.modele.*;
import ecole.gestion.vue.*;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.*;
import myconnections.DBConnection;

/**
 *
 * @author fabian
 */
public class GestEcole {

    PresenterEnseignant pe;
    PresenterCours pc;
    PresenterClasse pcl;
    PresenterSalle ps;
    PresenterInfos pi;
    Scanner sc = new Scanner(System.in);

    public void gestion(int mode) {
        DAOClasse mdcl = null;
        DAOCours mdc = null;
        DAOEnseignant mde = null;
        DAOSalle mds = null;
        DAOInfos mdi = null;
        switch (mode) {
            case 1:
                mdcl = new ModeleClasse();
                mdc = new ModeleCours();
                mde = new ModeleEnseignant();
                mds = new ModeleSalle();
                mdi = new ModeleInfos();
                break;
            case 2:
                Connection dbConnect = null;
                try {
                    dbConnect = DBConnection.getConnection();
                } catch (Exception e) {

                }
                if (dbConnect == null) {
                    System.out.println("impossible de se connecter");
                    System.exit(1);
                }

                mdcl = new ModeleClasseDB();
                mdc = new ModeleCoursDB();
                mde = new ModeleEnseignantDB();
                mds = new ModeleSalleDB();
                mdi = new ModeleInfosDB();
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
        VueInfos vuei = new VueInfos();
        pi = new PresenterInfos(mdi, vuei, ps, pc, pe, pcl);
        VueClasse vuecl = new VueClasse();
        pcl = new PresenterClasse(mdcl, vuecl, ps, pc, pe, pi);
        pi.setPcl(pcl);

        do {
            System.out.println("1.Enseignant\n2.Classes\n3.Cours\n4.Salle\n5.Infos\n6.Fin");
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
                    pi.gestion();
                    break;
                case "6":
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
