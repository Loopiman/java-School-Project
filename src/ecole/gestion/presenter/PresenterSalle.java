package ecole.gestion.presenter;

import ecole.gestion.modele.*;
import ecole.gestion.vue.VueSalle;
import ecole.metier.Salle;

import java.util.*;
import methods.Controle;

/**
 *
 * @author fabian
 */
public class PresenterSalle {

    private DAOSalle mds = new ModeleSalle();
    private VueSalle vues = new VueSalle();

    public PresenterSalle(DAOSalle mds, VueSalle vues) {
        this.mds = mds;
        this.vues = vues;
    }

    public void gestion() {
        do {
            int ch = vues.menu();
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    affSalle();
                    break;
                case 3:
                    recherche();
                    break;
                case 4:
                    modification();
                    break;
                case 5:
                    suppression();
                    break;
                case 6:
                    return;
            }
        } while (true);

    }

    public void ajout() {
        Salle news = vues.create();
        news = mds.create(news);
        if (news == null) {
            vues.displayMsg("erreur lors de la création de la salle - doublon");
            return;
        }

        vues.displayMsg("Salle créée");
    }

    protected void affSalle() {
        vues.affAll(mds.readAll());
    }

    public Salle recherche() {
        String nrech = vues.read();

        Salle s = new Salle(nrech, 0);
        s = mds.read(s);
        if (s == null) {
            vues.displayMsg("Salle introuvable");
            return null;
        }
        vues.display(s);
        return s;
    }

    public void modification() {
        Salle s = recherche();

        if (s != null) {
            s = vues.update(s);
            mds.update(s);
        }
    }

    public void suppression() {
        Salle s = recherche();
        if (s != null) {
            String rep;
            do {
                rep = vues.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                boolean res = mds.delete(s);
                if (res) {
                    vues.displayMsg("Salle supprimée");
                } else {
                    vues.displayMsg("erreur lors de la suppression");
                }
            }
        }
    }

    protected Salle affAll() {
        String chs;
        List<Salle> lp = mds.readAll();
        vues.affAll(mds.readAll());
        do {
            do {
                chs = vues.getMsg("numéro de l'élément choisi (0 pour aucun) :");
            } while (!Controle.verifInteger(chs));
            int ch = Integer.parseInt(chs);
            if (ch == 0) {
                return null;
            }
            if (ch >= 1 && ch <= lp.size()) {
                return lp.get(ch - 1);
            }
        } while (true);
    }

}
