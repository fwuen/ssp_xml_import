package provider;

import model.Production;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

public class ProductionProvider {
    EntityManager em;

    public ProductionProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }

    public void writeProduction(Production production) {
        em.getTransaction().begin();
        em.persist(production);
        em.getTransaction().commit();
    }

    public void updateProduction(Production production) {
        em.getTransaction().begin();
        em.merge(production);
        em.getTransaction().commit();
    }

    public Production findProductionById(int id) {
        return em.find(Production.class, id);
    }

    public void removeProduction(Production production) {
        em.getTransaction().begin();
        if (!em.contains(production)) {
            production = em.merge(production);
        }
        em.remove(production);
        em.getTransaction().commit();
    }

    public List<Production> findAllProductions() {
        Query query = em.createQuery("SELECT e FROM Production e");
        return (List<Production>) query.getResultList();
    }

    public List<Production> findProductionsByProductionDate() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Production> q = cb.createQuery(Production.class);
        Root<Production> from = q.from(Production.class);

        Predicate startDatePredicate = cb.greaterThanOrEqualTo(from.get("prTimestamp"), new Timestamp(System.currentTimeMillis()));

        q.select(from).where(startDatePredicate);
        q.orderBy(cb.asc(from.get("prTimestamp")));
        Query query = em.createQuery(q);
        List<Production> productions = query.getResultList();
        return productions;
    }
}
