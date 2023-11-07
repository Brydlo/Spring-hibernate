package sklep.alternatywne_dostepy_do_bazy;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sklep.model.Product;

@Controller
@RequestMapping("/alt2/products")
public class ProductController_v2 {
    @Autowired
    private EntityManager em;

    @GetMapping(produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String readAll() {
        return em.createNamedQuery("Product.findAll", Product.class)
                .getResultStream()
                .map(product -> " * " + product.getProductName() + " za cenę " + product.getPrice() + "\n")
                .collect(Collectors.joining());
    }

    @GetMapping(path = "/{id}", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String readOne(@PathVariable("id") Integer productId) {
        Product product = em.find(Product.class, productId);
        if(product != null) {
            return product.getProductName() + " za cenę " + product.getPrice();
        } else {
            return "Nie ma produktu o numerze " + productId;
        }
    }

    @GetMapping(path = "/szukaj", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String szukaj(String name) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE productName = :nazwa", Product.class);
        query.setParameter("nazwa", name);
        Product product = query.getSingleResult();
        if(product != null) {
            return product.getProductName() + " za cenę " + product.getPrice();
        } else {
            return "Nie ma produktu o nazwie " + name;
        }
    }

}
