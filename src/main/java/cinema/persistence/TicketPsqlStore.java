package cinema.persistence;

import cinema.model.Ticket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class TicketPsqlStore.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.12.2020
 */
public class TicketPsqlStore implements Store<Ticket> {
    private static final TicketPsqlStore STORE = new TicketPsqlStore();
    private static final ConnectorDB CONNECTOR_DB = ConnectorDB.getInstance();
    private static final Log LOG = LogFactory.getLog(TicketPsqlStore.class.getName());
    private static final String FIND_ALL = "SELECT * FROM halls ORDER BY id;";
    private static final String FIND_BY_ID = "SELECT * FROM halls WHERE row = ? and place = ?;";
    private static final String CHECK_STATUS = "SELECT status_free FROM halls WHERE row = ? AND place = ?;";
    private static final String CHANGE_STATUS = "UPDATE halls SET status_free = ?  WHERE row = ? AND place = ?;";

    private TicketPsqlStore() {
    }

    /**
     * Gets store.
     *
     * @return the store
     */
    public static TicketPsqlStore getStore() {
        return STORE;
    }

    private Connection connect() throws SQLException {
        return CONNECTOR_DB.getConnection();
    }

    @Override
    public Map<Ticket, Boolean> findAll() {
        Map<Ticket, Boolean> places = new LinkedHashMap<>();
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    places.put(new Ticket(it.getInt("row"),
                                    it.getInt("place"),
                                    it.getInt("price")),
                            it.getBoolean("status_free"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return places;
    }

    @Override
    public Ticket findById(int row, int place) {
        Ticket ticket = new Ticket(0, 0, 0);
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, row);
            ps.setInt(2, place);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt("id"));
                    ticket.setRow(rs.getInt("row"));
                    ticket.setPlace(rs.getInt("place"));
                    ticket.setPrice(rs.getInt("price"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ticket;
    }

    @Override
    public boolean buy(Ticket model) {
        boolean result = false;
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(CHANGE_STATUS)) {
            ps.setBoolean(1, false);
            ps.setInt(2, model.getRow());
            ps.setInt(3, model.getPlace());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean checkStatus(Ticket model) {
        boolean result = false;
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(CHECK_STATUS)) {
            ps.setInt(1, model.getRow());
            ps.setInt(2, model.getPlace());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getBoolean("status_free");
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean refund(Ticket model) {
        boolean result = false;
        try (Connection cn = connect(); PreparedStatement ps = cn.prepareStatement(CHANGE_STATUS)) {
            ps.setBoolean(1, true);
            ps.setInt(2, model.getRow());
            ps.setInt(3, model.getPlace());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}
