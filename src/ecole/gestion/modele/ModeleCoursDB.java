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
import java.util.List;
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
            System.out.println("error : "+ e);
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
            return false;
        }
    }

    @Override
    public Cours read(Cours erech) {
        String code = erech.getCode();
        String req = "select * from api_cours where code = ?";
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
            System.out.println("okokokok");
            return null;
        }
    }

    @Override
    public Cours update(Cours obj) {
        CoursDB pdb = (CoursDB) obj;
        String req = "update api_cours set intitule=?,nhs=? where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(3, pdb.getId_cours());
            pstm.setString(1, obj.getIntitule());
            pstm.setInt(2, obj.getNhs());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return null;
            }
            return read(obj);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Cours> readAll() {
        String req = "select * from api_cours";
        List<Cours> lc = new ArrayList<>();
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
