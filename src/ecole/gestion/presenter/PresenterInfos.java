package ecole.gestion.presenter;

import ecole.gestion.modele.DAOInfos;
import ecole.gestion.modele.ModeleInfos;
import ecole.gestion.vue.VueInfos;
import ecole.metier.Infos;

import methods.Controle;

import java.util.List;

/**
 *
 * @author fabia
 */
public class PresenterInfos {

    private DAOInfos mds = new ModeleInfos();
    private VueInfos vues = new VueInfos();
    private PresenterSalle ps;
    private PresenterCours pc;
    private PresenterEnseignant pe;
    private PresenterClasse pcl;

    public PresenterInfos(DAOInfos mds, VueInfos vues, PresenterSalle ps, PresenterCours pc, PresenterEnseignant pe, PresenterClasse pcl) {
        this.mds = mds;
        this.vues = vues;
        this.ps = ps;
        this.pc = pc;
        this.pe = pe;
        this.pcl = pcl;
    }

    public void setPcl(PresenterClasse pcl) {
        this.pcl = pcl;
    }

    public void gestion() {
        do {
            int ch = vues.menu();
            switch (ch) {
                case 1:
                    affInfos();
                    break;
                case 2:
                    suppression();
                    break;
                case 3:
                    return;
            }
        } while (true);

    }

    protected void affInfos() {
        vues.affAll(mds.readAll());
    }

    protected void suppression() {
        Infos s = affAll();
        if (s != null) {
            String rep;
            do {
                rep = vues.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                boolean res = mds.delete(s);
                pe.suppressionInfos(s);
                pc.suppressionInfos(s);
                pcl.suppressionInfos(s);
                ps.suppressionInfos(s);
                if (res) {
                    vues.displayMsg("Infos supprimée");
                }
            }
        }
    }

    protected Infos affAll() {
        String chs;
        List<Infos> lp = mds.readAll();
        vues.affAll(mds.readAll());
        do {
            do {
                chs = vues.getMsg("numéro de l'élément choisi (0 pour aucun) :");
            } while (!Controle.verifInteger(chs));
            int ch = Integer.parseInt(chs);
            System.out.println(ch);
            if (ch == 0) {
                return null;
            }
            if (ch >= 1 && ch <= lp.size()) {
                int i = 0;
                for (Infos s : lp) {
                    if (ch - 1 == i) {
                        return s;
                    }
                    i++;
                }
            }
        } while (true);
    }

    protected void add(Infos i) {
        mds.add(i);
    }
}
