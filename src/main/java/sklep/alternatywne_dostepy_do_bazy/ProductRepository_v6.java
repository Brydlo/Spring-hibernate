package sklep.alternatywne_dostepy_do_bazy;

import org.springframework.data.jpa.repository.JpaRepository;

import sklep.model.Product;

// We wcześniejszych wersjach pisało się tu adnotację @Repository.
// @Repository
public interface ProductRepository_v6 extends JpaRepository<Product, Integer> {

}
