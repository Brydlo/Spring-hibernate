package sklep.alternatywne_dostepy_do_bazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sklep.model.Product;
import sklep.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/alt7/products")
public class ProductController_v7 {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String readAll(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String readOne(@PathVariable("id") Integer productId, Model model) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product";
        } else {
            return "missing_product";
        }
    }

    @GetMapping("/szukaj")
    public String szukaj(Model model,
                                  String name,
                                  BigDecimal min,
                                  BigDecimal max) {
        List<Product> products = List.of();
        if(name != null && !name.isEmpty() && min == null && max == null) {
            products = productRepository.findByProductNameContainsIgnoringCase(name);
        } else if ((name == null || name.isEmpty()) && (min != null || max != null)) {
            if(min == null) {
                min = BigDecimal.ZERO;
            }
            if(max == null) {
                max = BigDecimal.valueOf(1000_000_000);
            }
            products = productRepository.findByPriceBetween(min, max);
        } else if (name != null && !name.isEmpty() && (min != null || max != null)) {
            if(min == null) {
                min = BigDecimal.ZERO;
            }
            if(max == null) {
                max = BigDecimal.valueOf(1000_000_000);
            }
            products = productRepository.findByProductNameContainingIgnoringCaseAndPriceBetween(name, min, max);
        }
        model.addAttribute("products", products);
        return "wyszukiwarka2";
    }

}
