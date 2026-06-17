package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.dao.MacchinaDAO;
import org.example.dao.MarcaDAO;
import org.example.dao.StalloDAO;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.json.JavalinJackson;
import org.example.model.Macchina;
import org.example.model.Stallo;

public class Server {

    private static MacchinaDAO macchinaDAO = new MacchinaDAO();
    private static StalloDAO stalloDAO = new StalloDAO();
    private static MarcaDAO marcaDAO = new MarcaDAO();

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("src/main/resources/static", Location.EXTERNAL);
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            }));
        }).start(7070);

        app.get("/ping", ctx -> ctx.result("Server funzionante!"));

        app.get("/macchine", ctx -> {
            ctx.json(macchinaDAO.getAll());
        });

        app.get("/macchine/{colonna}/{valore}", ctx -> {
            String colonna = ctx.pathParam("colonna");
            String valore = ctx.pathParam("valore");
            Macchina m = macchinaDAO.getByColonna(colonna, valore);
            if (m != null) {
                ctx.json(m);
            } else {
                ctx.status(404).result("Macchina non trovata");
            }
        });

        app.get("/marche", ctx -> {
            ctx.json(marcaDAO.getAll());
        });

        // Tutti gli stalli liberi
        app.get("/stalli", ctx -> {
            ctx.json(stalloDAO.getAll());
        });

        // Inserisci macchina
        app.post("/macchine", ctx -> {
            Macchina m = ctx.bodyAsClass(Macchina.class);

            // Controllo targa duplicata
            if (macchinaDAO.ottieniTarghe().contains(m.getTarga())) {
                ctx.status(409).result("Targa già presente!");
                return;
            }

            // Controllo posizione occupata
            if (stalloDAO.ottieniStalli().contains(m.getIdStallo())) {
                ctx.status(410).result("Posizione già occupata!");
                return;
            }

            // Inserisci nuovo stallo e macchina
            Stallo s = new Stallo();
            s.setPosizione(Integer.toString(m.getIdStallo()));
            stalloDAO.insert(s);
            int idStallo = stalloDAO.ottieniID(m.getIdStallo());
            m.setIdStallo(idStallo);
            macchinaDAO.insert(m);

            ctx.status(201).result("Macchina inserita!");
        });

        // Elimina macchina per targa
        app.delete("/macchine/{targa}", ctx -> {
            String targa = ctx.pathParam("targa");
            macchinaDAO.delete("targa", targa);
            ctx.result("Macchina eliminata!");
        });

    }
}