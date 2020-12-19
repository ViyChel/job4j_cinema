package cinema.persistence;

import cinema.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class AccountPsqlStore.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 15.12.2020
 */
public class AccountPsqlClient implements Client<Account> {
    private static final AccountPsqlClient STORE_ACCOUNT = new AccountPsqlClient();
    private static final TicketPsqlStore STORE_TICKET = TicketPsqlStore.getStore();
    private static final ConnectorDB CONNECTOR_DB = ConnectorDB.getInstance();
    private static final Log LOG = LogFactory.getLog(TicketPsqlStore.class.getName());
    private static final String FIND_ALL = "SELECT * FROM accounts;";
    private static final String FIND_BY_ID = "SELECT * FROM accounts WHERE id = ?;";
    private static final String CREATE = "INSERT INTO accounts (name, phone, halls_row, halls_place) VALUES (?, ?, ?,"
            + " ?);";
    private static final String DELETE = "DELETE FROM accounts WHERE id = ?;";

    private AccountPsqlClient() {
    }

    /**
     * Gets store account.
     *
     * @return the store account
     */
    public static AccountPsqlClient getStoreAccount() {
        return STORE_ACCOUNT;
    }

    private Connection connect() throws SQLException {
        return CONNECTOR_DB.getConnection();
    }

    @Override
    public Collection<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Account account = new Account(it.getInt("id"),
                            it.getString("name"),
                            it.getString("phone"));
                    Integer row = it.getObject("row", Integer.class);
                    Integer place = it.getObject("place", Integer.class);
                    if (row != null && place != null) {
                        account.setTicket(STORE_TICKET.findById(row, place));
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public Account save(Account model) {
        try (Connection cn = connect();
             PreparedStatement ps = cn.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, model.getName());
            ps.setString(2, model.getPhone());
            ps.setInt(3, model.getTicket().getRow());
            ps.setInt(4, model.getTicket().getPlace());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    model.setId(id.getInt(1));
                }
            }
            STORE_TICKET.buy(model.getTicket());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return model;
    }

    @Override
    public Account findById(int id) {
        Account account = new Account(0, "", "");
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account.setId(rs.getInt("id"));
                    account.setName(rs.getString("name"));
                    account.setPhone(rs.getString("phone"));
                    Integer row = rs.getObject("row", Integer.class);
                    Integer place = rs.getObject("place", Integer.class);
                    if (row != null && place != null) {
                        account.setTicket(STORE_TICKET.findById(row, place));
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            Account account = STORE_ACCOUNT.findById(id);
            result = ps.executeUpdate() > 0;
            if (result) {
                STORE_TICKET.refund(account.getTicket());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}
