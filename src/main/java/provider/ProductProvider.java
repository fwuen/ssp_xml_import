package provider;

import model.Product;

import javax.persistence.*;
import java.util.List;

public class ProductProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    private EntityManager em;

    public ProductProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }

    public void writeProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    public Product findProductById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT e FROM Product e");
        return (List<Product>) query.getResultList();
    }
}
