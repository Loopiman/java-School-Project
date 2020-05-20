package ecole.metier;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Cours
 *
 */
public class Cours implements Comparable<Cours> {

    /**
     * identifiant unique du cours
     */
    protected String code;

    /**
     * intitulé du cours
     */
    protected String intitule;

    /**
     * nombre d'heures par semaines
     */
    protected int nhs;

    /**
     * constructeur par défaut
     */
    protected Set<Infos> listeInfos = new HashSet();

    public Cours() {

    }

    /**
     *
     * constructeur paramétré
     *
     * @param code
     * @param intitule
     * @param nhs
     */
    public Cours(String code, String intitule, int nhs) {
        this.code = code;
        this.intitule = intitule;
        this.nhs = nhs;
    }

    /**
     * getter de l'identifiant unique
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * setter de l'identifiant unique
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getter de l'intitulé du cours
     *
     * @return intitule
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * setter de l'intitulé du cours
     *
     * @param intitule
     */
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    /**
     * getter du nombre d'heures par semaine
     *
     * @return nhs
     */
    public int getNhs() {
        return nhs;
    }

    /**
     * setter du nombre d'heures par semaine
     *
     * @param nhs
     */
    public void setNhs(int nhs) {
        this.nhs = nhs;
    }

    public Set<Infos> getListeInfos() {
        return listeInfos;
    }

    public void setListeInfos(Set<Infos> listeInfos) {
        this.listeInfos = listeInfos;
    }

    /**
     * hashcode de l'identifiant unique
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.code);
        return hash;
    }

    /**
     * methode equals de l'identifiant unique
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
        final Cours other = (Cours) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Cours c) {
        return this.code.compareTo(c.getCode());
    }

    @Override
    public String toString() {
        return "Cours{" + "code=" + code + ", intitule=" + intitule + ", nhs=" + nhs + '}';
    }

}
