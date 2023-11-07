package sklep.alternatywne_dostepy_do_bazy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sklep.model.Product;

import java.util.List;


@Controller
@RequestMapping("/alt3/products")
public class ProductController_v3 {
    @Autowired
    private EntityManager em;

    @GetMapping
    public String readAll(Model model) {
        List<Product> products = em.createNamedQuery("Product.findAll", Product.class).getResultList();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String readOne(@PathVariable("id") Integer productId, Model model) {
        Product product = em.find(Product.class, productId);
        if(product != null) {
            model.addAttribute("product", product);
            return "product";
        } else {
            return "missing_product";
        }
    }

    @GetMapping("/szukaj")
    public String szukaj(String name, Model model) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE productName = :nazwa", Product.class);
        query.setParameter("nazwa", name);
        List<Product> products = query.getResultList();
        model.addAttribute("products", products);
        return "wyszukiwarka";
    }
}
