package sklep.alternatywne_dostepy_do_bazy;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sklep.model.Product;

@Repository
public class ProductRepository_v4 {
	@Autowired
	private EntityManager em;
	
	public List<Product> findAll() {
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
		return query.getResultList();
	}
	
	public Optional<Product> findById(int productId) {
		return Optional.ofNullable(em.find(Product.class, productId));
	}
	
	public List<Product> findByProductName(String name) {
		final String sql = "SELECT p FROM Product p WHERE p.productName = :name";
		TypedQuery<Product> query = em.createQuery(sql, Product.class);
		query.setParameter("name", name);
		return query.getResultList();
	}
}
