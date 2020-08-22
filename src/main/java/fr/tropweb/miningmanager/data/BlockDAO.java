package fr.tropweb.miningmanager.data;

public interface BlockDAO<T> {

    boolean exist(T e);

    void save(T e);

    void delete(T e);
}
