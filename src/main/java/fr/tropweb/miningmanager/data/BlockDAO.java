package fr.tropweb.miningmanager.data;

import java.util.List;

public interface BlockDAO<T> {

    List<T> list();

    T select(final T e);

    void save(final T e);

    void delete(final T e);

    boolean exist(final T e);

    T randomBlock(final String world);

    String randomWord(final List<String> worlds);

    void update(final T e);

    void unblock();
}
