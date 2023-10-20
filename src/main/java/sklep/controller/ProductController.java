package sklep.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sklep.model.Product;
import sklep.repository.ProductRepository;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public String readAll(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/{numer}")
    public String readOne(Model model, @PathVariable Integer numer) {
        Optional<Product> product = productRepository.findById(numer);

        if(product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product";
        } else {
            model.addAttribute("product_id", numer);
            return "missing_product";
        }
    }

    @GetMapping("/products/szukaj")
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


