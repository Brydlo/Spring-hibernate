package sklep.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sklep.model.Customer;
import sklep.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductName(String name);
    List<Product> findByProductNameIgnoringCase(String name);
    List<Product> findByProductNameContainsIgnoringCase(String name);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product> findByProductNameContainingIgnoringCaseAndPriceBetween(String name, BigDecimal min, BigDecimal max);
}
