package ecole.gestion.modele;

import ecole.metier.Infos;
import ecole.metier.Salle;
import ecole.metier.db.SalleDB;

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
public class ModeleSalleDB implements DAOSalle {

    protected Connection dbConnect;

    public ModeleSalleDB() {
        dbConnect = DBConnection.getConnection();
    }



    @Override
    public Salle create(Salle obj) {
        String req1 = "insert into api_salle(sigle_salle,capacite) values( ?,  ?)";
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
        String req = "select * from api_salle where sigle_salle = ? order by sigle_salle";
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
        int cap = 0;
        String req = "update api_salle set sigle_salle=?,capacite=? where id_salle =  ?";
        String req1 = "select min(cl.nbr_eleves) as MIN from api_classe cl left join api_info i on cl.id_classe = i.id_classe "
                + " left join api_salle s on i.id_salle = s.id_salle where s.sigle_salle = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); PreparedStatement pstm1 = dbConnect.prepareStatement(req1)) {

            pstm1.setString(1, s.getSigleSalle());

            try (ResultSet rs = pstm1.executeQuery()) {
                if (rs.next()) {
                    cap = rs.getInt("MIN");
                }
            }
            System.out.println(cap);
            if (s.getCapacite() < cap) {
                System.out.println("Impossible de baisser la capactite actuelle: une classe d'une capacité supérieure est occupée.");
                return null;
            }
            pstm.setInt(3, s.getId_salle());
            pstm.setString(1, obj.getSigleSalle());
            pstm.setInt(2, obj.getCapacite());
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
    public boolean delete(Salle obj) {
        String req = "delete from api_salle where sigle_salle= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);) {
            pstm.setString(1, obj.getSigleSalle());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("erreur : La salle est attribué à une info");
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
        return true;
    }

    @Override
    public Set<Salle> readAll() {
        String req = "select * from api_salle order by sigle_salle";
        Set<Salle> ls = new TreeSet<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idsalle = rs.getInt("ID_SALLE");
                String sigle = rs.getString("SIGLE_SALLE");
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
