package org.example.model;

public class Marca {

    private String nomeMarca;

    public Marca() {
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    @Override
    public String toString() {
        return nomeMarca;
    }
}
