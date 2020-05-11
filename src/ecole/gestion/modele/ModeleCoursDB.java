package ecole.gestion.modele;

/**
 *
 * @author fabia
 */
import ecole.metier.db.CoursDB;
import ecole.metier.Cours;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        String req = "update api_cours set intitule=?,nhs=? where id_cours = ?";
        String req1 = "select e.matricule as MAT, e.chargerest as CHARGE\n"
                + " from api_enseignant e\n"
                + " left join api_info i on e.id_enseignant = i.id_enseignant\n"
                + " left join api_cours c on c.id_cours = i.id_cours\n"
                + " where c.code = ?";
        String req2 = "select nhs from api_cours where code = ?";
        String req3 = "update api_enseignant set chargeRest = ? where matricule = ?";
        String req4 = "select min(e.chargerest) as MIN from api_enseignant e\n"
                + "left join api_info i on i.id_enseignant = e.id_enseignant\n"
                + "left join api_cours c on c.id_cours = i.id_cours\n"
                + "where c.code = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
                PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2);
                PreparedStatement pstm3 = dbConnect.prepareStatement(req3);
                PreparedStatement pstm4 = dbConnect.prepareStatement(req4);) {

            pstm2.setString(1, pdb.getCode());
            int oldnhs = 0;
            int min = 0;
            pstm4.setString(1, pdb.getCode());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    oldnhs = rs.getInt("NHS");
                }
            }
            int dif = (pdb.getNhs() - oldnhs);

            pstm1.setString(1, pdb.getCode());
            try (ResultSet rs = pstm4.executeQuery()) {
                if (rs.next()) {
                    min = rs.getInt("MIN");
                }
            }
            if (dif > min) {
                System.out.println("Impossible d'augmenter le nombre d'heures de cours: Les enseignants n'ont pas une charge assez grande");
                return null;
            }
            try (ResultSet rs = pstm1.executeQuery()) {
                
                while (rs.next()) {
                    String matricule = rs.getString("MAT");
                    int chargerest = rs.getInt("CHARGE");
                    System.out.println("matricule : " + matricule + " chargerest : " + chargerest);
                    
                    pstm3.setInt(1, chargerest - dif);
                    pstm3.setString(2, matricule);
                    int n = pstm3.executeUpdate();
                    if(n == 0){
                        System.out.println("problème");
                    }
                }
            }

            pstm.setInt(3, pdb.getId_cours());
            pstm.setString(1, obj.getIntitule());
            pstm.setInt(2, obj.getNhs());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return null;
            }
            return read(obj);
        } catch (Exception e) {
            System.out.println(e);
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
    public Set<Cours> readAll() {
        String req = "select * from api_cours order by code";
        Set<Cours> lc = new HashSet<>();
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
