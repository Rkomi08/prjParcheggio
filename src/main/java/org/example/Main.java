package org.example;

import org.example.dao.MacchinaDAO;
import org.example.dao.MarcaDAO;
import org.example.dao.StalloDAO;
import org.example.model.Macchina;
import org.example.model.Marca;
import org.example.model.Stallo;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static MacchinaDAO macchinaDAO = new MacchinaDAO(); //"Attributo" pubblico per tutti i metodi del main
    private static StalloDAO stalloDAO = new StalloDAO();
    private static MarcaDAO marcaDAO = new MarcaDAO();


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int scelta = 0; //inizzializzazione delle variabili che uso nel switch case
        String query = "";
        PreparedStatement statement = null;



        do{
            System.out.println("Cosa volete fare?");
            System.out.println("   1) Vedere le macchine che ci sono nel Parchegio");
            System.out.println("   2) Vedere specifica macchina");
            System.out.println("   3) Entrare nel parcheggio");
            System.out.println("   4) Uscire dal parcheggio");
            scelta = sc.nextInt();

            switch (scelta){
                case 1:
                    List<Macchina> lista = macchinaDAO.getAll();
                    System.out.println(lista.toString());
                    break;

                case 2:
                    Macchina car = new Macchina();
                    System.out.println("Da quale colonna volete cercare la macchina?");
                    System.out.println("   1) ID dell'auto");
                    System.out.println("   2) Targa");
                    System.out.println("   3) Posizione");
                    System.out.println("   0) Uscire");
                    int scelta2 = sc.nextInt();
                    switch (scelta2){
                        case 1:
                            System.out.println("Id dell'auto: ");
                            int id = sc.nextInt();
                            if(id > 0 && id <= macchinaDAO.getAll().size()) {
                                car = macchinaDAO.getByColonna("Id_Auto", Integer.toString(id));
                                System.out.println(car.toString());
                            }else{
                                System.out.println("Id Invalido");
                            }
                            break;

                        case 2:
                            System.out.println("Targa dell'auto (AA123AA): ");
                            String targa = sc.next();
                            if(targa.length() == 7 && isTargaPresente(targa)) {
                                car = macchinaDAO.getByColonna("Targa", targa);
                                System.out.println(car.toString());
                            }else{
                                System.out.println("Targa invalida");
                            }
                            break;
                        case 3:
                            System.out.println("Posizione dell'auto nel pargheggio: ");
                            int posizone = sc.nextInt();
                            if(isPosizionePresente(posizone)) {
                                car = macchinaDAO.getByColonna("posizione", Integer.toString(posizone));
                                System.out.println(car.toString());
                            }else{
                                System.out.println("Posizione invalida");
                            }
                            break;

                        case 0:
                            System.out.println("Uscita effettuata.");
                            break;

                        default:
                            System.out.println("Scelta invalida!");

                    }


                    break;


                case 3:
                    System.out.println("Targa dell'auto (AA123AA): ");
                    String targa = sc.next();
                    if(targa.length() == 7 && !isTargaPresente(targa)){
                        System.out.println("Quale delle marche è l'auto?: ");
                        List<Marca> list = new ArrayList<>();
                        list = marcaDAO.getAll();
                        for(int i = 0; i < list.size(); i++){
                            System.out.println((i+1) + ") " +  list.get(i).toString());
                        }
                        System.out.println((list.size()+1) + ") Altro");
                        int marca = sc.nextInt()-1;
                        if(marca <= 0 || marca > list.size() + 1){
                            System.out.println("Marca invalida");
                        }else {
                            System.out.println("In che posizione è l'auto?: ");
                            int pos = sc.nextInt();
                            Stallo s = new Stallo();
                            s.setPosizione(Integer.toString(pos));
                            if(!isPosizionePresente(pos)){
                                stalloDAO.insert(s);
                                int idnewStallo = stalloDAO.ottieniID(pos);
                                Macchina m = new Macchina();
                                m.setIdStallo(idnewStallo);
                                System.out.println(pos);
                                System.out.println(idnewStallo);
                                m.setTarga(targa);
                                String marcaSTR = "";
                                if(marca > list.size()){
                                    marcaSTR = "Altro";
                                }else{
                                    marcaSTR = list.get(marca).getNomeMarca();
                                }
                                m.setMarca(marcaSTR);
                                macchinaDAO.insert(m);
                            }else {
                                System.out.println("Posizione invalida dell'auto nel parcheggio");
                            }
                        }

                    }else{
                        System.out.println("Targa invalida");
                    }
                    break;

                case 4:
                    System.out.println("Targa dell'auto (AA123AA): ");
                    String targaUscita = sc.next();
                    if(isTargaPresente(targaUscita)){
                        macchinaDAO.delete("targa", targaUscita);
                        System.out.println("Uscita effettuata!");
                    }else{
                        System.out.println("Macchina non presente");
                    }
                    break;


                default:
                    System.out.println("Scelta Invalida!");
            }

        }while(scelta != 0);

    }

    public static boolean isTargaPresente(String targa) {
        boolean isDuplicata = false;
        List<String> targhe = macchinaDAO.ottieniTarghe();
        int i = 0;
        while (i < targhe.size() && !isDuplicata) {
        if (targhe.get(i).equals(targa)) {
            isDuplicata = true;
        }
        i++;
    }
        return isDuplicata;

    }

    public static boolean isPosizionePresente(int posizione) {
        boolean isDuplicata = false;
        List<Integer> posizioni = stalloDAO.ottieniStalli();
        int i = 0;
        while (i < posizioni.size() && !isDuplicata) {
            if (posizioni.get(i) == posizione) {
                isDuplicata = true;
            }
            i++;
        }
        return isDuplicata;
    }

}