package sklep.alternatywne_dostepy_do_bazy;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sklep.model.Product;

@Controller
@RequestMapping("/alt5/products")
public class ProductController_v5 {
	@Autowired
	private ProductRepository_v5 productRepository;
	
	@GetMapping
	public String wszystkieProdukty(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/{id}")
	public String jedenProdukt(@PathVariable int id, Model model) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			model.addAttribute("product", product.get());
			return "product";
		} else {
			model.addAttribute("product_id", id);
			return "missing_product";
		}
	}
	
	@GetMapping("/szukaj")
	public String wyszukajProdukty(@RequestParam String name, Model model) {
		List<Product> products = productRepository.findByProductName(name);
		model.addAttribute("products", products);
		return "products";
	}
}
