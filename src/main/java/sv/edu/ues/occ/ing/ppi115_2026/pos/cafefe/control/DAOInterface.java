package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.List;

public interface DAOInterface<T> {
    public void crear(T reg) throws IllegalArgumentException, IllegalStateException;
    public void modificar(T reg) throws IllegalArgumentException, IllegalStateException;
    public void eliminar(Object id) throws IllegalArgumentException, IllegalStateException;
    public T findById(Object id) throws IllegalArgumentException, IllegalStateException;
    public List<T> findRange(int first, int max) throws IllegalArgumentException, IllegalStateException;
    public int contar() throws IllegalStateException;
}
