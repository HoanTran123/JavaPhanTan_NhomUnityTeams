package dao.impl;

import entity.ChiTietPhieuNhap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;

public abstract class GenericDAO<T> {

    protected EntityManager entityManager;

    public GenericDAO() {
        if (entityManager == null) {
            System.out.println("EntityManager is null in " + this.getClass().getName());
        }
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        if (entityManager == null) {
            System.err.println("EntityManager set to null in " + this.getClass().getName());
        } else {
            System.out.println("EntityManager initialized in " + this.getClass().getName());
        }
    }
    public List<T> findMany(String query, Class<T> clazz, Object... params) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        TypedQuery<T> typedQuery = entityManager.createQuery(query, clazz);
        setParameters(typedQuery, params);
        return typedQuery.getResultList();
    }
    public T findOne(String query, Class<T> clazz, Object... params) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        TypedQuery<T> typedQuery = entityManager.createQuery(query, clazz);
        setParameters(typedQuery, params);
        return typedQuery.getSingleResult();
    }

    public List<T> findManyWithPagination(String query, Class<T> clazz, Map<String, Object> params, int offset, int limit) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        TypedQuery<T> typedQuery = entityManager.createQuery(query, clazz);
        if (params != null) {
            params.forEach(typedQuery::setParameter);
        }
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    public int count(String query, Object... params) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        setParameters(typedQuery, params);
        return typedQuery.getSingleResult().intValue();
    }

    public boolean add(T entity) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(T entity) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(T entity) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    private void setParameters(TypedQuery<?> query, Object... params) {
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
    }


    protected List<ChiTietPhieuNhap> findMany(String query, Class<ChiTietPhieuNhap> chiTietPhieuNhapClass, String idPN) {
        TypedQuery<ChiTietPhieuNhap> typedQuery = entityManager.createQuery(query, chiTietPhieuNhapClass);
        typedQuery.setParameter("idPN", idPN);
        return typedQuery.getResultList();
    }
}