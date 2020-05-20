package ecole.gestion.modele;

/**
 *
 * @author fabia
 */
import ecole.metier.db.CoursDB;
import ecole.metier.Cours;
import ecole.metier.Infos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;
import java.util.TreeSet;

import myconnections.DBConnection;

/**
 *
 * @author fabia
 */
public class ModeleCoursDB implements DAOCours {

    protected Connection dbConnect;

    public ModeleCoursDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public void init() {

    }

    @Override
    public Cours create(Cours obj) {
        String req1 = "insert into api_cours(code, intitule, nhs) values(?,?,?)";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);) {
            pstm1.setString(1, obj.getCode());
            pstm1.setString(2, obj.getIntitule());
            pstm1.setInt(3, obj.getNhs());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            } else {
                return read(obj);
            }
        } catch (Exception e) {
            System.out.println("error : " + e);
            return null;
        }
    }

    @Override
    public Cours read(Cours erech) {
        String code = erech.getCode();
        String req = "select * from api_cours where code = ? order by code";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);) {

            pstm.setString(1, code);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id_cours = rs.getInt("ID_COURS");
                    String intitule = rs.getString("INTITULE");
                    int nhs = rs.getInt("NHS");
                    Cours c = new CoursDB(id_cours, code, intitule, nhs);
                    return c;

                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cours update(Cours obj) {
        CoursDB pdb = (CoursDB) obj;

        int nhs = 0;
        int min = 0;
        //requete pour mettre à jour la table api_cours
        String req = "update api_cours set intitule=?,nhs=? where id_cours = ?";

        //requete pour récupérer le matricule et la charge restante d'un enseignant (valeur qu'on modifie)
        //https://stackoverflow.com/questions/61750096/bug-executequery-java
        String req1 = "select e.matricule as MAT, e.chargerest as CHARGE\n"
                + " from api_enseignant e\n"
                + " left join api_info i on e.id_enseignant = i.id_enseignant\n"
                + " where i.id_cours = ?";

        //requete qui affiche l'ancienne durée du cours
        String req2 = "select nhs from api_cours where code = ?";

        //requete qui vérifie qu'un cours est présent dans la table info (pour savoir si on doit update les enseignants ou non)
        String req3 = "select count(id_info) as COUNT from api_info where id_cours = ?";

        //requete qui permet de récupérer la charge restante la plus petite pour éviter d'ajouter trop d'heures de cours par rapport à la charge qui reste
        String req4 = "select min(e.chargerest) as MIN from api_enseignant e\n"
                + "left join api_info i on i.id_enseignant = e.id_enseignant\n"
                + "where i.id_cours = ?";

        //requete qui met ajout la table enseignant
        String req5 = "update api_enseignant set chargerest = ? where matricule = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
                PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2);
                PreparedStatement pstm3 = dbConnect.prepareStatement(req3);
                PreparedStatement pstm4 = dbConnect.prepareStatement(req4);
                PreparedStatement pstm5 = dbConnect.prepareStatement(req5);) {

            //récupère l'ancienne durée de cours 
            pstm2.setString(1, pdb.getCode());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    nhs = rs.getInt("NHS");
                }
            }

            //différence entre l'ancienne heure de cours et la nouvelle
            int dif = nhs - pdb.getNhs();

            pstm3.setInt(1, pdb.getId_cours());

            try (ResultSet rs = pstm3.executeQuery()) {
                if (rs.next()) {
                    //on vérifie qu'il y a au moins le cours une fois dans la table info si oui alors il faudra mettre à jours les enseignants, sinon juste mettre à jour la table cours
                    if (rs.getInt("COUNT") > 0) {
                        pstm4.setInt(1, pdb.getId_cours());
                        try (ResultSet rs1 = pstm4.executeQuery()) {
                            if (rs1.next()) {
                                // on récupère la charge minimum parmis tout les enseignants pour ne pas mettre une durée de cours trop grande (condition pas encore faite)
                                min = rs1.getInt("MIN");
                            }
                        }
                        pstm1.setInt(1, pdb.getId_cours());
                        try (ResultSet rs1 = pstm1.executeQuery()) {
                            while (rs1.next()) {
                                //on récupère chaque enseignant de la table 
                                System.out.println("matricule : " + rs1.getString("MAT") + " charge : " + rs1.getInt("CHARGE"));

                                //mettre à jour chaque prof /!\ C EST ICI QUE CA NE MET PAS A JOUR LE DEUXIEME ENSEIGNANT /!\
                                pstm5.setInt(1, rs1.getInt("CHARGE") + dif);
                                pstm5.setString(2, rs1.getString("MAT"));
                                pstm5.executeUpdate();
                            }
                        }
                    }
                }
            }

            //mise à jour de la table cours
            System.out.println(pdb.toString());
            pstm.setInt(3, pdb.getId_cours());
            pstm.setString(1, obj.getIntitule());
            pstm.setInt(2, obj.getNhs());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return null;
            }
            return read(obj);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public boolean delete(Cours obj) {
        String req = "delete from api_cours where code= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, obj.getCode());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("erreur : Le cours est attribué à une info");
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
        return true;
    }

    @Override
    public Set<Cours> readAll() {
        String req = "select * from api_cours order by code";
        Set<Cours> lc = new TreeSet<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idcours = rs.getInt("ID_COURS");
                String code = rs.getString("CODE");
                String intitule = rs.getString("INTITULE");
                int nhs = rs.getInt("NHS");
                lc.add(new CoursDB(idcours, code, intitule, nhs));
            }
            return lc;
        } catch (Exception ex) {
            System.out.println("error" + ex);
            return lc;
        }
    }
}
