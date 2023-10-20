package sklep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sklep.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
