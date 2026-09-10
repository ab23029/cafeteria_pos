package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public abstract class DefaultDAO<T> implements DaoInterface<T> {

    protected final Class<T> entityClass;

    public DefaultDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public void create(T entity) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager nulo.");
        }
        if (entity == null) {
            throw new IllegalArgumentException("La entidad a crear no puede ser nula.");
        }
        em.persist(entity);
    }

    @Override
    public T edit(T entity) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager nulo.");
        }
        if (entity == null) {
            throw new IllegalArgumentException("La entidad a editar no puede ser nula.");
        }
        return em.merge(entity);
    }

    @Override
    public void remove(T entity) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager nulo.");
        }
        if (entity == null) {
            throw new IllegalArgumentException("La entidad a eliminar no puede ser nula.");
        }
        em.remove(em.merge(entity));
    }

    @Override
    public T find(Object id) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager nulo.");
        }
        if (id == null) {
            throw new IllegalArgumentException("El ID de búsqueda no puede ser nulo.");
        }
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findRange(int first, int max) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager nulo.");
        }
        if (first < 0 || max <= 0) {
            throw new IllegalArgumentException("Parámetros de paginación inválidos (first >= 0, max > 0).");
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        cq.select(cq.from(entityClass));
        TypedQuery<T> q = em.createQuery(cq);
        q.setFirstResult(first);
        q.setMaxResults(max);
        return q.getResultList();
    }

    @Override
    public int count() throws IllegalStateException {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager nulo.");
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> rt = cq.from(entityClass);
        cq.select(cb.count(rt));
        TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult().intValue();
    }
}