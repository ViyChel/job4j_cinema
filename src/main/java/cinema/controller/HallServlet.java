package cinema.controller;

import cinema.model.Ticket;
import cinema.persistence.Store;
import cinema.persistence.TicketPsqlStore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Class HallServlet.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.12.2020
 */
@WebServlet(urlPatterns = {"/place.do"})
public class HallServlet extends HttpServlet {
    private static final Store<Ticket> TICKET_STORE = TicketPsqlStore.getStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Map<Ticket, Boolean> places = TICKET_STORE.findAll();
        JsonArray jsonArray = new JsonArray();
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        places.forEach((k, v) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("row", k.getRow());
            jsonObject.addProperty("place", k.getPlace());
            jsonObject.addProperty("price", k.getPrice());
            jsonObject.addProperty("status", v);
            jsonArray.add(jsonObject);
        });
        writer.println(jsonArray);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        int row = Integer.parseInt(req.getParameter("row"));
        int place = Integer.parseInt(req.getParameter("place"));
        Ticket ticket = TICKET_STORE.findById(row, place);
        int price = ticket.getPrice();
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        writer.println(price);
        writer.flush();
    }
}
