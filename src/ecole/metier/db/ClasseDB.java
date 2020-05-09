package ecole.metier.db;

import ecole.metier.Classe;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Classe
 *
 */
public class ClasseDB extends Classe {

    /**
     * identifiant unique de la classe en DB
     */
    protected int id_classe;

    public ClasseDB(int id_classe, String sigleClasse, int annee, String specialite, int nbrEleves) {
        super(sigleClasse, annee, specialite, nbrEleves);
        this.id_classe = id_classe;
    }

 

    public int getId_classe() {
        return id_classe;
    }

    public void setId_classe(int id_classe) {
        this.id_classe = id_classe;
    }

    @Override
    public String toString() {
        return "ClasseDB{" +super.toString()+ " id_classet=" + id_classe + '}';
    }

    
}
