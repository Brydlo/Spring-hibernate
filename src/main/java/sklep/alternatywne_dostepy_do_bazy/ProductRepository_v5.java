package sklep.alternatywne_dostepy_do_bazy;

import java.util.List;
import java.util.Optional;

import sklep.model.Product;

public interface ProductRepository_v5 {

	List<Product> findAll();

	Optional<Product> findById(int productId);

	List<Product> findByProductName(String name);

}