package org.example.model;

import java.time.LocalDateTime;

public class Macchina {

    private int id;
    private String targa;
    private String marca;
    private int idStallo;
    private LocalDateTime oraEntrata;

    public Macchina() {
    }

    public int getId() {
        return id;
    }
    public String getTarga() {
        return targa;
    }
    public String getMarca() {
        return marca;
    }
    public int getIdStallo() {
        return idStallo;
    }
    public LocalDateTime getOraEntrata() {
        return oraEntrata;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTarga(String targa) {
        this.targa = targa;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public void setIdStallo(int idStallo) {
        this.idStallo = idStallo;
    }
    public void setOraEntrata(LocalDateTime oraEntrata) {
        this.oraEntrata = oraEntrata;
    }

    @Override
    public String toString() {
        String out = "";
        out += " MACCHINA: Targa: " + targa + " | Marca: " + marca + " | Posizione: " + idStallo + " | Ora di Entrata: " + oraEntrata + "\n";
        return out;
    }
}
