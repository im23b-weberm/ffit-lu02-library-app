package ch.bzz;
import model.Book;  // <-- füge das hinzu


import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class JavalinMain {

    public static void main(String[] args) {
        // Start Javalin auf Port 7070
        Javalin app = Javalin.create().start(7070);

        // Route /books
        app.get("/books", JavalinMain::handleListBooks);
    }

    private static void handleListBooks(Context ctx) {
        // query param als String
        String limitParam = ctx.queryParam("limit");
        int limit = 0; // Standard: kein Limit
        if (limitParam != null) {
            try {
                limit = Integer.parseInt(limitParam);
            } catch (NumberFormatException e) {
                // optional: Fehlerstatus zurückgeben, falls ungültig
                ctx.status(400).result("Invalid limit parameter");
                return;
            }
        }

        // Liste der Bücher aus DB
        List<Book> books = LibraryAppMain.getBooksFromDB();

        // falls limit gesetzt ist
        if (limit > 0 && limit < books.size()) {
            books = books.subList(0, limit);
        }

        try {
            // JSON serialisieren
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(books);
            ctx.contentType("application/json");
            ctx.result(json);
        } catch (Exception e) {
            ctx.status(500).result("Error serializing books");
            e.printStackTrace();
        }
    }

}
