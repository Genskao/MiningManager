package fr.tropweb.miningmanager.dao;

import java.util.List;

public interface BlockDAO<T> {

    /**
     * List all elements from database.
     *
     * @return list of all elements
     */
    List<T> list();

    /**
     * Select block.
     *
     * @param block block object with minimal elements
     * @return block from database
     */
    T select(final T block);

    /**
     * Save block.
     *
     * @param block block you want to save
     */
    void save(final T block);

    void delete(final T block);

    boolean exist(final T block);

    T randomBlock(final String world);

    String randomWord(final List<String> worlds);

    void update(final T block);

    void unblock();
}
