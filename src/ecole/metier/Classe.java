package ecole.metier;

import java.util.*;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Classe
 *
 */
public class Classe implements Comparable<Classe>{

    /**
     * identifiant unique de la classe
     */
    protected String sigleClasse;

    /**
     * annee en cours de la classe
     */
    protected int annee;

    /**
     * type de classe(informatique, comptabilité, etc.)
     */
    protected String specialite;

    /**
     * nombre d'élèves de la classe
     */
    protected int nbrEleves;

    protected Set<Infos> listeInfos = new HashSet();

    /**
     * constructeur par défaut
     */
    public Classe() {

    }

    /**
     *
     * constructeur paramétré
     *
     * @param sigleClasse identifiant unique de la classe
     * @param annee année en cours de la classe
     * @param specialite type de classe (informatique, comptabilité, etc.)
     * @param nbrEleves nombre d'élèves dans la classe
     *
     */
    public Classe(String sigleClasse, int annee, String specialite, int nbrEleves) {
        this.sigleClasse = sigleClasse;
        this.annee = annee;
        this.specialite = specialite;
        this.nbrEleves = nbrEleves;
    }

    /**
     * setter identifiant unique de la classe
     *
     * @param sigleClasse
     *
     */
    public void setSigleClasse(String sigleClasse) {
        this.sigleClasse = sigleClasse;
    }

    /**
     * getter identifiant unique de la classe
     *
     * @return sigleClasse
     */
    public String getSigleClasse() {
        return sigleClasse;
    }

    /**
     * setter annee en cours
     *
     * @param annee
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    /**
     * getter annee en cours
     *
     * @return annee
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * setter specialité de la classe
     *
     * @param specialite
     */
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    /**
     * getter sepcialité de la classe
     *
     * @return specialite
     */
    public String getSpecialite() {
        return specialite;
    }

    /**
     * setter nombre d'élèves dans la classe
     *
     * @param nbrEleves
     */
    public void setNbrEleves(int nbrEleves) {
        this.nbrEleves = nbrEleves;
    }

    /**
     * getter nombre d'élèves dans la classe
     *
     * @return nbrEleves
     */
    public int getNbrEleves() {
        return nbrEleves;
    }

    public Set<Infos> getListeInfos() {
        return listeInfos;
    }

    public void setListeInfos(Set<Infos> listeInfos) {
        this.listeInfos = listeInfos;
    }

    /**
     * hashcode du sigle
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.sigleClasse);
        return hash;
    }

    /**
     * methode equals pour vérifier que le sigle est unique
     *
     * @param obj
     * @return true
     */
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
        final Classe other = (Classe) obj;
        if (!Objects.equals(this.sigleClasse, other.sigleClasse)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Classe cl) {
        return this.sigleClasse.compareTo(cl.getSigleClasse());
    }
    
    @Override
    public String toString() {
        return "Classe{" + "sigleClasse=" + sigleClasse + ", annee=" + annee + ", specialite=" + specialite + ", nbrEleves=" + nbrEleves + '}';
    }

}
