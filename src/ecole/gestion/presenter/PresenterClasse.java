package ecole.gestion.presenter;

import ecole.metier.*;
import ecole.gestion.modele.*;
import ecole.gestion.vue.VueClasse;
import java.util.List;
import java.util.Set;
import methods.Controle;

/**
 *
 * @author fabia
 */
public class PresenterClasse {

    private DAOClasse mdcl;
    private VueClasse vuecl;
    private PresenterSalle ps;
    private PresenterCours pc;
    private PresenterEnseignant pe;

    public PresenterClasse(DAOClasse mdcl, VueClasse vuecl, PresenterSalle ps, PresenterCours pc, PresenterEnseignant pe) {
        this.mdcl = mdcl;
        this.vuecl = vuecl;
        this.ps = ps;
        this.pc = pc;
        this.pe = pe;
    }

    public void gestion() {
        do {
            int ch = vuecl.menu();
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    affClasse();
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
                    addInfo();
                    break;
                case 7:
                    return;
            }
        } while (true);

    }

    public void ajout() {
        Classe newe = vuecl.create();
        newe = mdcl.create(newe);
        if (newe == null) {
            vuecl.displayMsg("erreur lors de la création de la classe - doublon");
            return;
        }

        vuecl.displayMsg("Classe créé");
    }

    protected void affClasse() {
        vuecl.affAll(mdcl.readAll());
    }

    public Classe recherche() {
        String nrech = vuecl.read();

        Classe cl = new Classe(nrech, 0, "", 0);
        cl = mdcl.read(cl);
        if (cl == null) {
            vuecl.displayMsg("classe introuvable");
            return null;
        }
        vuecl.display(cl);
        return cl;
    }

    public void modification() {
        Classe cl = recherche();

        if (cl != null) {
            cl = vuecl.update(cl);
            mdcl.update(cl);
        }
    }

    public void suppression() {
        Classe cl = recherche();
        if (cl != null) {
            String rep;
            do {
                rep = vuecl.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                boolean res = mdcl.delete(cl);
                if (res) {
                    vuecl.displayMsg("Classe supprimé");
                }
            }
        }
    }

    private void addInfo() {

        Classe cl = affAll();
        if (cl == null) {
            return;
        }
        /*if(!cl.getListeInfos().isEmpty()){
            System.out.println("classe deja attribué");
            return;
        }*/

        Enseignant e = pe.affAll();

        if (e == null) {
            return;
        }
        Cours c = pc.affAll();
        if (c == null) {
            return;
        }

        if (e.getChargeRest() < c.getNhs()) {
            System.out.println("l'enseignant n'a pas une charge assez grande");
            return;
        }
        e.setChargeRest(e.getChargeRest()-c.getNhs());

        Salle s = ps.affAll();
        if (s == null) {
            return;
        }
        if (s.getCapacite() < cl.getNbrEleves()) {
            System.out.println("salle pas assez grande");
            return;
        }

        Infos i = new Infos(c, s, e);
        boolean add = e.getListeInfos().add(i);
        boolean res = mdcl.add(cl, i);
        if (res) {
            vuecl.displayMsg("info ajoutée");
        } else {
            vuecl.displayMsg("info non ajoutée");
        }

    }

    protected Classe affAll() {
        String chs;
        Set<Classe> lp = mdcl.readAll();
        if (lp.isEmpty()) {
            System.out.println("vide");
            return null;
        } else {
            vuecl.affAll(mdcl.readAll());
            do {
                do {
                    chs = vuecl.getMsg("numéro de l'élément choisi (0 pour aucun) :");
                } while (!Controle.verifInteger(chs));
                int ch = Integer.parseInt(chs);
                if (ch == 0) {
                    return null;
                }
                if (ch >= 1 && ch <= lp.size()) {
                    int i = 0;
                    for (Classe c : lp) {
                        if (ch - 1 == i) {
                            return c;
                        }
                        i++;
                    }
                }
            } while (true);
        }
    }
}
