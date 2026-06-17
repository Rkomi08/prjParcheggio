package org.example.dao;

import org.example.db.DataBaseManager;
import org.example.model.Stallo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StalloDAO {

    // 🔹 INSERT
    public void insert(Stallo s) {
        String sql = "INSERT INTO stallo (posizione) VALUES (?)";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,s.getPosizione());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 SELECT TUTTI
    public List<Stallo> getAll() {
        List<Stallo> lista = new ArrayList<>();

        String sql = "SELECT * FROM stallo";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Stallo s = new Stallo();

                s.setId(rs.getInt("id_stallo"));
                s.setPosizione(Integer.toString(rs.getInt("posizione")));

                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Integer> ottieniStalli() {
        List<Integer> stalli = new ArrayList<>();

        String sql = "SELECT posizione FROM stallo";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int posizione = rs.getInt("posizione");
                stalli.add(posizione);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stalli;
    }

    public int ottieniID(int posizione) {
        int id = 0;
        boolean tf = false;
        String sql = "SELECT id_stallo, posizione FROM stallo";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next() && !tf) {
                if (rs.getInt("posizione") == posizione){
                    id = rs.getInt("id_stallo");
                    tf = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;

    }

    // 🔹 DELETE
    public void delete(String colonna, String valore) {
        String sql = "DELETE FROM stallo WHERE " + colonna + " = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valore);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}