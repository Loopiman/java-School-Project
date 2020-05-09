package ecole.gestion.modele;

import ecole.metier.Salle;
import ecole.metier.db.SalleDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import myconnections.DBConnection;

/**
 *
 * @author fabia
 */
public class ModeleSalleDB implements DAOSalle {

    protected Connection dbConnect;

    public ModeleSalleDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Salle create(Salle obj) {
        String req1 = "insert into api_salle(sigle,capacite) values( ?,  ?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req1)) {
            pstm.setString(1, obj.getSigleSalle());
            pstm.setInt(2, obj.getCapacite());

            int n = pstm.executeUpdate();

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
    public Salle read(Salle prrech) {

        String sigle = prrech.getSigleSalle();
        String req = "select * from api_salle where sigle = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);) {
            pstm.setString(1, sigle);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id_salle = rs.getInt("ID_SALLE");
                    int capacite = rs.getInt("CAPACITE");
                    Salle s = new SalleDB(id_salle, sigle, capacite);
                    return s;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Salle update(Salle obj) {
        SalleDB s = (SalleDB) obj;
        String req = "update api_salle set sigle=?,capacite=? where id_salle =  ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(3, s.getId_salle());
            pstm.setString(1, obj.getSigleSalle());
            pstm.setInt(2, obj.getCapacite());
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
    public boolean delete(Salle obj) {
        String req = "delete from api_salle where sigle= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, obj.getSigleSalle());
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
    public List<Salle> readAll() {
        String req = "select * from api_salle";
        List<Salle> ls = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idsalle = rs.getInt("ID_SALLE");
                String sigle = rs.getString("SIGLE");
                int capacite = rs.getInt("CAPACITE");
                ls.add(new SalleDB(idsalle, sigle, capacite));
            }
            return ls;
        } catch (Exception e) {
            System.out.println("error" + e);
            return ls;
        }
    }
}
