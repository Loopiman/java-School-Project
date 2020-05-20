package ecole.gestion.presenter;

import ecole.metier.Cours;
import ecole.gestion.modele.*;
import ecole.gestion.vue.VueCours;
import ecole.metier.Infos;

import methods.Controle;

import java.util.*;

/**
 *
 * @author fabian
 */
public class PresenterCours {

    private DAOCours mdc;
    private VueCours vuec;

    public PresenterCours(DAOCours mdc, VueCours vuec) {
        this.mdc = mdc;
        this.vuec = vuec;
    }

    public void gestion() {
        do {
            int ch = vuec.menu();
            mdc.init();
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    affCours();
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
        Cours newe = vuec.create();
        newe = mdc.create(newe);
        if (newe == null) {
            vuec.displayMsg("erreur lors de la création du cours - doublon");
            return;
        }

        vuec.displayMsg("Cours créé");
    }

    protected void affCours() {
        vuec.affAll(mdc.readAll());
    }

    public Cours recherche() {
        String nrech = vuec.read();

        Cours c = new Cours(nrech, "", 0);
        c = mdc.read(c);
        if (c == null) {
            vuec.displayMsg("cours introuvable");
            return null;
        }
        vuec.display(c);
        return c;
    }

    public void modification() {
        Cours c = recherche();

        if (c != null) {
            c = vuec.update(c);
            mdc.update(c);
        }
    }

    public void suppression() {
        Cours c = recherche();
        if (c != null) {
            String rep;
            do {
                rep = vuec.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                boolean res = mdc.delete(c);
                if (res) {
                    vuec.displayMsg("Cours supprimé");
                }
            }
        }
    }

    public void suppressionInfos(Infos i) {
        mdc.deleteInfo(i);
    }

    protected Cours affAll() {
        String chs;
        Set<Cours> lp = mdc.readAll();
        vuec.affAll(mdc.readAll());
        do {
            do {
                chs = vuec.getMsg("numéro de l'élément choisi (0 pour aucun) :");
            } while (!Controle.verifInteger(chs));
            int ch = Integer.parseInt(chs);
            if (ch == 0) {
                return null;
            }
            if (ch >= 1 && ch <= lp.size()) {
                int i = 0;
                for (Cours c : lp) {
                    if (ch - 1 == i) {
                        return c;
                    }
                    i++;
                }
            }
        } while (true);
    }

}
