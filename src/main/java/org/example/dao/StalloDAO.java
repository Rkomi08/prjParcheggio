package org.example.dao;

import org.example.db.DataBaseManager;
import org.example.model.Stallo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StalloDAO {

    // 🔹 INSERT
    public void insert(Stallo s) {
        String sql = "INSERT INTO STALLO (Posizione) VALUES (?)";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getPosizione());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 SELECT TUTTI
    public List<Stallo> getAll() {
        List<Stallo> lista = new ArrayList<>();

        String sql = "SELECT * FROM STALLO";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Stallo s = new Stallo();

                s.setId(rs.getInt("Id_Stallo"));
                s.setPosizione(Integer.toString(rs.getInt("Posizione")));

                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Integer> ottieniStalli() {
        List<Integer> stalli = new ArrayList<>();

        String sql = "SELECT Posizione FROM STALLO";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int posizione = rs.getInt("Posizione");
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
        String sql = "SELECT Id_Stallo, Posizione FROM STALLO";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next() && !tf) {
                if (rs.getInt("Posizione") == posizione) {
                    id = rs.getInt("Id_Stallo");
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
        String sql = "DELETE FROM STALLO WHERE " + colonna + " = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valore);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}