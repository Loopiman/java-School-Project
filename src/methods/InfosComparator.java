package methods;

import ecole.metier.Infos;
import java.util.Comparator;

/**
 *
 * @author fabia
 */
public class InfosComparator implements Comparator<Infos> {

    @Override
    public int compare(Infos i1, Infos i2) {
        int inf1 = i1.hashCode();
        int inf2 = i2.hashCode();
        if (inf1 > inf2) {
            return 1;
        }
        else if(inf1 < inf2) return -1;
        else return 0;

    }
}
