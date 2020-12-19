package cinema.persistence;

import java.util.Collection;

/**
 * The interface Client.
 *
 * @param <T> the type parameter
 */
public interface Client<T> {
    /**
     * Find all collection.
     *
     * @return the collection
     */
    Collection<T> findAll();

    /**
     * Save t.
     *
     * @param model the model
     * @return the t
     */
    T save(T model);

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(int id);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(int id);
}
