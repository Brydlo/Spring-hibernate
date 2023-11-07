package sklep.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sklep.model.Customer;
import sklep.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
