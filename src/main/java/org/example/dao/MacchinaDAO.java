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
        String sql = "INSERT INTO macchina (targa, marca, id_stallo, ora_entrata) VALUES (?, ?, ?, ?)";

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

        String sql = "SELECT M.Id_Auto, M.Targa, M.Marca, S.Posizione, M.Ora_Entrata\n" +
                "FROM MACCHINA M \n" +
                "INNER JOIN STALLO S\n" +
                "ON M.Id_Stallo = S.Id_Stallo;";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Macchina m = new Macchina();

                m.setId(rs.getInt("id_auto"));
                m.setTarga(rs.getString("targa"));
                m.setMarca(rs.getString("marca"));
                m.setIdStallo(rs.getInt("posizione"));
                m.setOraEntrata(rs.getTimestamp("ora_entrata").toLocalDateTime());

                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 SELECT PER COLONNA
    public Macchina getByColonna(String colonna, String valore) {
        String sql = "SELECT M.Id_Auto, M.Targa, M.Marca, S.Posizione, M.Ora_Entrata FROM MACCHINA M INNER JOIN STALLO S ON M.Id_Stallo = S.Id_Stallo WHERE " + colonna + " = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valore);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Macchina m = new Macchina();

                m.setId(rs.getInt("id_auto"));
                m.setTarga(rs.getString("targa"));
                m.setMarca(rs.getString("marca"));
                m.setIdStallo(rs.getInt("posizione"));
                m.setOraEntrata(rs.getTimestamp("ora_entrata").toLocalDateTime());

                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> ottieniTarghe() {
        List<String> targhe = new ArrayList<>();

        String sql = "SELECT targa FROM macchina";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String targa = rs.getString("targa");
                targhe.add(targa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return targhe;
    }


    // 🔹 DELETE
    public void delete(String colonna, String valore) {
        String sql = "DELETE FROM macchina WHERE " + colonna + " = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valore);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}