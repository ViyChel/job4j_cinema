package cinema.controller;

import cinema.model.Account;
import cinema.model.Ticket;
import cinema.persistence.AccountPsqlClient;
import cinema.persistence.Client;
import cinema.persistence.Store;
import cinema.persistence.TicketPsqlStore;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class AccountServlet.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 16.12.2020
 */
@WebServlet(urlPatterns = {"/buy.do"})
public class AccountServlet extends HttpServlet {
    private static final Client<Account> ACCOUNT_STORE = AccountPsqlClient.getStoreAccount();
    private static final Store<Ticket> TICKET_STORE = TicketPsqlStore.getStore();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            while (reader.ready()) {
                sb.append(reader.readLine());
            }
        }
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
            String name = jsonObject.get("name").toString();
            String phone = jsonObject.get("phone").toString();
            int row = Integer.parseInt(jsonObject.get("row").toString());
            int place = Integer.parseInt(jsonObject.get("place").toString());
            Ticket ticket = TICKET_STORE.findById(row, place);
            Account account = new Account(0, name, phone, ticket);
            ACCOUNT_STORE.save(account);
            TICKET_STORE.buy(ticket);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
