package ecole.metier.db;

import ecole.metier.Cours;
import ecole.metier.Enseignant;
import ecole.metier.Infos;
import ecole.metier.Salle;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Infos
 */
public class InfosDB extends Infos {

    /**
     * cours concerné
     */
    protected int id_infos;
    protected int id_cours;
    protected int id_enseignant;
    protected int id_classe;
    protected int id_salle;

    public InfosDB(int id_infos, int id_cours, int id_enseignant, int id_classe, int id_salle, Cours cours, Salle salle, Enseignant enseignant) {
        super(cours, salle, enseignant);
        this.id_infos = id_infos;
        this.id_cours = id_cours;
        this.id_enseignant = id_enseignant;
        this.id_classe = id_classe;
        this.id_salle = id_salle;
    }

    public int getId_infos() {
        return id_infos;
    }

    public void setId_infos(int id_infos) {
        this.id_infos = id_infos;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public int getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(int id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public int getId_classe() {
        return id_classe;
    }

    public void setId_classe(int id_classe) {
        this.id_classe = id_classe;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id_infos;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InfosDB other = (InfosDB) obj;
        if (this.id_infos != other.id_infos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "idInfos=" + id_infos + ", idCours=" + id_cours + ", idEnseignant=" + id_enseignant + ", idClasse=" + id_classe
                + ", idSalle=" + id_salle + '}';
    }
}
