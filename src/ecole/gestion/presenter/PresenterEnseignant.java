package ecole.gestion.presenter;

import ecole.gestion.vue.VueEnseignant;
import ecole.metier.Enseignant;
import ecole.gestion.modele.DAOEnseignant;
import java.util.*;
import methods.Controle;

/**
 *
 * @author fabian
 */
public class PresenterEnseignant {

    private DAOEnseignant mde;
    private VueEnseignant vuee = new VueEnseignant();

    public PresenterEnseignant(DAOEnseignant mde, VueEnseignant vuee) {
        this.mde = mde;
        this.vuee = vuee;
    }

    public void gestion() {
        do {
            int ch = vuee.menu();
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    affEnseignant();
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
        Enseignant newe = vuee.create();
        newe = mde.create(newe);
        if (newe == null) {
            vuee.displayMsg("erreur lors de la création de l'enseignant - doublon");
            return;
        }

        vuee.displayMsg("enseignant créé");
    }

    protected void affEnseignant() {
        vuee.affAll(mde.readAll());
    }

    public Enseignant recherche() {
        String nrech = vuee.read();

        Enseignant e = new Enseignant(nrech, "", "", "", 0, null, null);
        e = mde.read(e);
        if (e == null) {
            vuee.displayMsg("enseignant introuvable");
            return null;
        }
        vuee.display(e);

        return e;
    }

    public void modification() {
        Enseignant e = recherche();

        if (e != null) {
            e = vuee.update(e);
            mde.update(e);
        }
    }

    public void suppression() {
        Enseignant e = recherche();
        if (e != null) {
            String rep;
            do {
                rep = vuee.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                boolean res = mde.delete(e);
                if (res) {
                    vuee.displayMsg("Enseignant supprimé");
                }
            }
        }
    }

    protected Enseignant affAll() {
        String chs;
        List<Enseignant> lp = mde.readAll();
        if (lp.isEmpty()) {
            System.out.println("vide");
            return null;
        } else {
            vuee.affAll(mde.readAll());
            do {
                do {
                    chs = vuee.getMsg("numéro de l'élément choisi (0 pour aucun) :");
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

}
