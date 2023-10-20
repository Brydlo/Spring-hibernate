package sklep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sklep.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductName(String name);

    List<Product> findByProductNameIgnoringCase(String name);

    List<Product> findByProductNameContainsIgnoringCase(String name);
}
