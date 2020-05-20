package ecole.gestion.modele;

import ecole.metier.Infos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabia
 */
public class ModeleInfos implements DAOInfos {

    private List<Infos> listeInfos = new ArrayList();

    @Override
    public boolean delete(Infos srech) {

        if (srech != null) {
            listeInfos.remove(srech);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean add(Infos i) {
        listeInfos.add(i);
        return true;
    }

    @Override
    public List<Infos> readAll() {
        return listeInfos;
    }
}
