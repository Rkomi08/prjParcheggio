package org.example.dao;

import org.example.db.DataBaseManager;
import org.example.model.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    // 🔹 INSERT
    public void insert(Marca m) {
        String sql = "INSERT INTO MARCA (Marca) VALUES (?)";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNomeMarca());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 SELECT TUTTI
    public List<Marca> getAll() {
        List<Marca> lista = new ArrayList<>();

        String sql = "SELECT * FROM MARCA";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca m = new Marca();

                m.setNomeMarca(rs.getString("Marca"));

                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 DELETE
    public void delete(String nomeMarca) {
        String sql = "DELETE FROM MARCA WHERE Marca = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeMarca);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}