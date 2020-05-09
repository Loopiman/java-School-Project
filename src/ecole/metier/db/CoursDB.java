package ecole.metier.db;

import ecole.metier.Cours;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Cours
 *
 */
public class CoursDB extends Cours{

    /**
     * identifiant unique du coursDB
     */
    protected int id_cours;

    public CoursDB(int id_cours, String code, String intitule, int nhs) {
        super(code, intitule, nhs);
        this.id_cours = id_cours;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    @Override
    public String toString() {
        return "CoursDB{"+super.toString() + "id_cours=" + id_cours + '}';
    }


}
