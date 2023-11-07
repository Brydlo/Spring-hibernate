package sklep.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import sklep.model.Customer;
import sklep.repository.CustomerRepository;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String listaKlientow(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/{email}")
    public String jedenKlient(Model model, @PathVariable("email") String email) {
        Optional<Customer> customer = customerRepository.findById(email);
        if(customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma klienta o mailu " + email);
        }
    }

    @GetMapping("/new")
    public String nowy(@ModelAttribute Customer customer) {
        return "customer_form";
    }

    @GetMapping("/{email}/edit")
    public String edytuj(Model model, @PathVariable("email") String email) {
        Optional<Customer> customer = customerRepository.findById(email);
        if(customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer_form";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma klienta o mailu " + email);
        }
    }

    @PostMapping({"/new", "/{email}/edit"})
    public String zapisz(Model model,
                         @ModelAttribute @Valid Customer customer,
                         BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "customer_form";
        } else {
            customerRepository.save(customer);

            // return "redirect:/customers";
            return "customer_form";
        }
    }

}


