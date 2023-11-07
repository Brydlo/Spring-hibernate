package sklep.basket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Configuration
public class BasketConfiguration {
    // Adnotacja Configuration powoduje, że:
    // - Spring tworzy obiekt tej klasy (BasketConfiguration)
    // - dla każdej meody oznaczonej @Bean uruchamia tę metodę, a jej wynik rejestruje jako bean
    // - gdy typ wynikowy "znaczy dla Springa coś specjalnego", to Spring weźmie to pod uwagę,
    //   właśnie w ten sposób często podaje się Springowi elementy konfiguracji

    @Bean
    HttpSessionListener createListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                HttpSession sesja = se.getSession();
//   	     	sesja.setMaxInactiveInterval(30); // pół minuty i sesja wygasa
//   	     	System.out.println("sessionCreated " + sesja.getId());
                Basket basket = new Basket();
                sesja.setAttribute("basket", basket);
            }
        };
    }

}


