package methods;

import ecole.metier.Cours;
import java.util.Comparator;

/**
 *
 * @author fabia
 */
public class SigleCoursComparator implements Comparator<Cours>{
    @Override
    public int compare(Cours c1, Cours c2){
        String code1 = c1.getCode();
        String code2 = c2.getCode();
        
        return code1.compareTo(code2);
        
    }
}
