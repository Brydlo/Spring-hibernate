package sklep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

// https://docs.spring.io/spring-security/reference/servlet/getting-started.html

// W tej wersji różnym rolom dajemy różne poziomy dostępu.
// (inaczej mówiąc: dla róznych adresów mamy różne wymagania)

// To pomogło mi napisać poprawną konfigurację w "nowym stylu":
// https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
// Kluczowe było dodanie linii z DispatcherType.FORWARD → bez tego Spring wymagał autentykacji na etapie przechodzenia do szablonu jsp (FORWARD) lub wyświetlenia błędu

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain configHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authz -> authz
                        // zezwalamy na działanie przekierowań wewnętrznych (szablony) i błędów
                        .anyRequest().permitAll()
                        //.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
//                        .requestMatchers("/", "/whoami", "/*.css").permitAll()
//                        .requestMatchers("/hello", "/time").permitAll()
//                        .requestMatchers("/alt?/**").authenticated() // zalogowany jako ktokolwiek
//                        .requestMatchers("/products/new", "/products/*/edit").hasAuthority("ROLE_manager")
//                        .requestMatchers("/products/**").permitAll() // kolejność reguł ma znaczenie
//                        .requestMatchers("/customers/new", "/customers/*/edit").hasRole("manager") // skrót na hasAuthority("ROLE_...")
//                        .requestMatchers("/customers/**").authenticated()
//                        .anyRequest().denyAll() // dobra praktyka - odrzucanie pozostałych zapytań; Spring domyślnie wymagałby "authenticated"
                )
                .formLogin(Customizer.withDefaults()); // albo .httpBasic(Customizer.withDefaults())
//   		 .csrf(authz -> authz.disable())
        return httpSecurity.build();
    }

    // Aspektem konfiguracji, który jest podawany w innej metodzie, jest zdefiniowany zbiór użytkowników.
    // W tej wersji definiujemy użytkowników w kodzie aplikacji ("in memory").
    @Bean
    InMemoryUserDetailsManager userDetailsService() {
        UserDetails[] users = {
                User.withUsername("ala").password("{noop}ala123").roles("manager", "worker").build(),
                User.withUsername("ola").password("{noop}ola123").roles("worker").build(),
        };
        return new InMemoryUserDetailsManager(users);
    }

}


