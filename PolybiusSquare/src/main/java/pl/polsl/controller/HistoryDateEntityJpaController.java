package pl.polsl.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.polsl.controller.exceptions.NonexistentEntityException;
import pl.polsl.math.HistoryDateEntity;

/**
 * This classes goal is to provide a wide range of operations that can be
 * performed on HistoryDateEntity table. It is a controller class.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class HistoryDateEntityJpaController implements Serializable {

    public HistoryDateEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * This method creates a new record in the table.
     *
     * @param historyDateEntity class which represents a table record
     */
    public void create(HistoryDateEntity historyDateEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historyDateEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * This class allows to edit contents of a table.
     *
     * @param historyDateEntity entity to edit
     * @throws NonexistentEntityException exception is thrown when entity doesnt
     * exist
     * @throws Exception
     */
    public void edit(HistoryDateEntity historyDateEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historyDateEntity = em.merge(historyDateEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = historyDateEntity.getId();
                if (findHistoryDateEntity(id) == null) {
                    throw new NonexistentEntityException("The historyDateEntity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * This class allows to delete recors from table.
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoryDateEntity historyDateEntity;
            try {
                historyDateEntity = em.getReference(HistoryDateEntity.class, id);
                historyDateEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyDateEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(historyDateEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method finds what a developer is looking for without having to manually
     * type sql query.
     *
     * @return list of elements which developer is looking for
     */
    public List<HistoryDateEntity> findHistoryDateEntityEntities() {
        return findHistoryDateEntityEntities(true, -1, -1);
    }

    /**
     * Method finds what a developer is looking for without having to manually
     * type sql query. It circumscribes results according to preferences.
     *
     * @return list of elements which developer is looking for
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<HistoryDateEntity> findHistoryDateEntityEntities(int maxResults, int firstResult) {
        return findHistoryDateEntityEntities(false, maxResults, firstResult);
    }

    /**
     * Actual method which looks for appriopriate records, invoked by
     * findHistoryDateEntityEntities (int maxResults, int firstResult)
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<HistoryDateEntity> findHistoryDateEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoryDateEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Find entity of a given id
     *
     * @param id id of entity
     * @return entity
     */
    public HistoryDateEntity findHistoryDateEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoryDateEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Method returns number of elemens in HistoryDate table
     *
     * @return number of elements
     */
    public int getHistoryDateEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoryDateEntity> rt = cq.from(HistoryDateEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
