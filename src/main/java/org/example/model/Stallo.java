package org.example.model;

public class Stallo {

    private int id;
    private String posizione;

    public Stallo() {
    }

    public int getId() {
        return id;
    }
    public String getPosizione() {
        return posizione;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "id=" + id +
                ", posizione='" + posizione + '\'' +
                '}';
    }
}
