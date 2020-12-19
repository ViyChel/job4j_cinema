package cinema.model;

import java.util.Objects;

/**
 * Class Ticket.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.12.2020
 */
public class Ticket {
    private int id;
    private int row;
    private int place;
    private int price;

    /**
     * Instantiates a new Ticket.
     *
     * @param row   the row
     * @param place the place
     * @param price the price
     */
    public Ticket(int row, int place, int price) {
        this.row = row;
        this.place = place;
        this.price = price;
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
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets row.
     *
     * @param row the row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets place.
     *
     * @return the place
     */
    public int getPlace() {
        return place;
    }

    /**
     * Sets place.
     *
     * @param place the place
     */
    public void setPlace(int place) {
        this.place = place;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return row == ticket.row
                && place == ticket.place
                && price == ticket.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, place, price);
    }
}
