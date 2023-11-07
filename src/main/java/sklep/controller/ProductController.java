package sklep.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sklep.model.Product;
import sklep.repository.ProductRepository;
import sklep.util.PhotoUtil;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String readAll(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{numer}")
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

    @GetMapping("/{id}/edit")
    public String editProduct(Model model, @PathVariable("id") Integer productId) {
        // W tej metodzie ładujemy dane istniejącego produktu i przechodzimy do formularza
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product_form";
        } else {
            model.addAttribute("product_id", productId);
            return "missing_product";
        }
    }

    @GetMapping("/new")
    public String newProduct() {
        // W tej metodzie wyświetlamy pusty formularz
        return "product_form";
    }

    @PostMapping({"/{id}/edit", "/new"})
    // Ta metoda zapisuje dane przysłane z formularza obojętnie, czy to było edit, czy new
    public String saveProduct(@Valid Product product,
                              BindingResult bindingResult) {
        // W tej wersji dane z wypełnionego formularza odbieramy w postaci jednego obiektu Product.
        // Spring sam wpisze dane do pól o takich samych nazwach.
        // Taki parametr od razu staje się częścią modelu (to jest tzw. ModelAttribute)
//        gdy dopiszemy adnotację VALID bez dodatkowych parametrów , Spring dokona walidacji obiektu PRZED uruchomieniem tej metody
//        Jeśli nie ma dodatkowego parametru BindingResult, a są będy walidacji, to Spring naszej metody nie wykona
//        Jeśłi metoda ma metodę BindingResult to metoda zawsze jest uruchamiana przez Spring
//        a w tym parametrze zawarte są informacje o przebiegu walidacji w tym błędy
//        gdy podczas zapisu (operacja save) obiekt nie spełnia warunków walidacji, jest wyrzucany  wyjątek
        if (bindingResult.hasErrors()) {
            System.out.println("Są błędy : " + bindingResult.getAllErrors());
//            normalnie wyświetlone zostałoby coś na stronie
        } else {
            System.out.println("id przed zapisem: " + product.getProductId());
            productRepository.save(product);
            System.out.println("id po zapisie: " + product.getProductId());
        }
        return "product_form";
    }



    @GetMapping(path="/{id}/photo", produces="image/jpeg")
    @ResponseBody
    public byte[] getPhoto(@PathVariable("id") Integer productId) {
        return photoUtil.readBytes(productId);
    }

    @Autowired
    private PhotoUtil photoUtil;
}


