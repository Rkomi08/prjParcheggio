package org.example.dao;

import org.example.db.DataBaseManager;
import org.example.model.Macchina;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MacchinaDAO {

    // 🔹 INSERT
    public void insert(Macchina m) {
        String sql = "INSERT INTO MACCHINA (Targa, Marca, Id_Stallo, Ora_Entrata) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTarga());
            ps.setString(2, m.getMarca());
            ps.setInt(3, m.getIdStallo());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 SELECT TUTTI
    public List<Macchina> getAll() {
        List<Macchina> lista = new ArrayList<>();

        String sql = "SELECT M.Id_Auto, M.Targa, M.Marca, S.Posizione, M.Ora_Entrata " +
                "FROM MACCHINA M " +
                "INNER JOIN STALLO S " +
                "ON M.Id_Stallo = S.Id_Stallo";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Macchina m = new Macchina();

                m.setId(rs.getInt("Id_Auto"));
                m.setTarga(rs.getString("Targa"));
                m.setMarca(rs.getString("Marca"));
                m.setIdStallo(rs.getInt("Posizione"));
                m.setOraEntrata(rs.getTimestamp("Ora_Entrata").toLocalDateTime());

                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 SELECT PER COLONNA
    public Macchina getByColonna(String colonna, String valore) {
        String sql = "SELECT M.Id_Auto, M.Targa, M.Marca, S.Posizione, M.Ora_Entrata " +
                "FROM MACCHINA M INNER JOIN STALLO S ON M.Id_Stallo = S.Id_Stallo WHERE " + colonna + " = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valore);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Macchina m = new Macchina();

                m.setId(rs.getInt("Id_Auto"));
                m.setTarga(rs.getString("Targa"));
                m.setMarca(rs.getString("Marca"));
                m.setIdStallo(rs.getInt("Posizione"));
                m.setOraEntrata(rs.getTimestamp("Ora_Entrata").toLocalDateTime());

                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> ottieniTarghe() {
        List<String> targhe = new ArrayList<>();

        String sql = "SELECT Targa FROM MACCHINA";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String targa = rs.getString("Targa");
                targhe.add(targa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return targhe;
    }

    // 🔹 DELETE
    public void delete(String colonna, String valore) {
        String sql = "DELETE FROM MACCHINA WHERE " + colonna + " = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valore);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}