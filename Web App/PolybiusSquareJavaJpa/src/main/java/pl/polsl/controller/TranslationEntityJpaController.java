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
import pl.polsl.math.TranslationEntity;

/**
 * This classes goal is to provide a wide range of operations that can be
 * performed on TranslationEntity table. It is a controller class.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class TranslationEntityJpaController implements Serializable {

    public TranslationEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * This method creates a new record in the table.
     *
     * @param translationEntity
     */
    public void create(TranslationEntity translationEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(translationEntity);
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
     * @param translationEntity entity to edit
     * @throws NonexistentEntityException exception is thrown when entity doesnt
     * exist
     * @throws Exception
     */
    public void edit(TranslationEntity translationEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            translationEntity = em.merge(translationEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = translationEntity.getId();
                if (findTranslationEntity(id) == null) {
                    throw new NonexistentEntityException("The TranslationEntity with id " + id + " no longer exists.");
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
            TranslationEntity translationEntity;
            try {
                translationEntity = em.getReference(TranslationEntity.class, id);
                translationEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The translationEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(translationEntity);
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
    public List<TranslationEntity> findTranslationEntityEntities() {
        return findTranslationEntities(true, -1, -1);
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
    public List<TranslationEntity> findTranslationEntityEntities(int maxResults, int firstResult) {
        return findTranslationEntities(false, maxResults, firstResult);
    }

    /**
     * Actual method which looks for appriopriate records, invoked by
     * findTranslationEntityEntities (int maxResults, int firstResult)
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<TranslationEntity> findTranslationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TranslationEntity.class));
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
    public TranslationEntity findTranslationEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TranslationEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Method returns number of elemens in TranslationEntity table
     *
     * @return number of elements
     */
    public int getTranslationEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TranslationEntity> rt = cq.from(TranslationEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
