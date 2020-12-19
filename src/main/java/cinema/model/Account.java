package cinema.model;

import java.util.Objects;

/**
 * Class Account.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 14.12.2020
 */
public class Account {
    private int id;
    private String name;
    private String phone;
    private Ticket ticket;

    /**
     * Instantiates a new Account.
     *
     * @param id     the id
     * @param name   the name
     * @param phone  the phone
     * @param ticket the ticket
     */
    public Account(int id, String name, String phone, Ticket ticket) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.ticket = ticket;
    }

    /**
     * Instantiates a new Account.
     *
     * @param id    the id
     * @param name  the name
     * @param phone the phone
     */
    public Account(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets ticket.
     *
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets ticket.
     *
     * @param ticket the ticket
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id
                && Objects.equals(name, account.name)
                && Objects.equals(phone, account.phone)
                && Objects.equals(ticket, account.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, ticket);
    }
}
