package cinema.persistence;

import java.util.Map;

/**
 * Interface Store.
 *
 * @param <T> the type parameter
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.12.2020
 */
public interface Store<T> {
    /**
     * Find all collection.
     *
     * @return the collection
     */
    Map<T, Boolean> findAll();

    /**
     * Find model.
     *
     * @param row .
     * @param place .
     * @return the model.
     */
    T findById(int row, int place);

    /**
     * Buy boolean.
     *
     * @param model the model
     * @return the boolean. True - purchase was successful, else false.
     */
    boolean buy(T model);

    /**
     * Check status boolean.
     *
     * @param model the model
     * @return the boolean. True, if the place is free, else false.
     */
    boolean checkStatus(T model);

    /**
     * Refund boolean.
     *
     * @param model the model
     * @return the boolean. True - freeing up space was successful, else false.
     */
    boolean refund(T model);
}
