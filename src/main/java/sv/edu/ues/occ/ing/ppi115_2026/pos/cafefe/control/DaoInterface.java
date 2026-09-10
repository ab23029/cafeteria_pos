package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.List;

public interface DaoInterface<T> {
    void create(T entity) throws IllegalArgumentException, IllegalStateException;
    T edit(T entity) throws IllegalArgumentException, IllegalStateException;
    void remove(T entity) throws IllegalArgumentException, IllegalStateException;
    T find(Object id) throws IllegalArgumentException, IllegalStateException;
    List<T> findRange(int first, int max) throws IllegalArgumentException, IllegalStateException;
    int count() throws IllegalStateException;
}