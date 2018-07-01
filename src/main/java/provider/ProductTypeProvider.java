package provider;

import model.ProductType;

import javax.persistence.*;
import java.util.List;

public class ProductTypeProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    EntityManager em;

    public ProductTypeProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }

    public void writeProductType(ProductType productType) {
        em.getTransaction().begin();
        em.persist(productType);
        em.getTransaction().commit();
    }

    public ProductType findProductTypeById(int id) {
        return em.find(ProductType.class, id);
    }

    public void removeProductType(ProductType productType) {
        em.getTransaction().begin();
        if (!em.contains(productType)) {
            productType = em.merge(productType);
        }
        em.remove(productType);
        em.getTransaction().commit();
    }

    public List<ProductType> findAllProductTypes() {
        Query query = em.createQuery("SELECT e FROM ProductType e");
        return (List<ProductType>) query.getResultList();
    }
}
