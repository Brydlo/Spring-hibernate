package sklep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

import javax.sql.DataSource;

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
   /* @Bean
    InMemoryUserDetailsManager userDetailsService() {
        UserDetails[] users = {
                User.withUsername("ala").password("{noop}ala123").roles("manager", "worker").build(),
                User.withUsername("ola").password("{noop}ola123").roles("worker").build(),
        };
        return new InMemoryUserDetailsManager(users);
    }
*/
    /* WERSJA W OPARCIU O BAZĘ DANYCH
      |
     \ /
     */
    // Aspektem konfiguracji, który jest podawany w innej metodzie, jest zdefiniowany zbiór użytkowników.
    // W tej wersji definiujemy użytkowników a oparciu o bazę danych SQL.
    @Bean
    JdbcUserDetailsManager userDetailsService2() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Mamy podać zapytanie SQL, które pozwoli Springowi odczytać informacje o userze na podstawie nazwy usera
        // w wyniku ma zwrócić rekord z trzeba kolumnami: nazwa, hasło, czy aktywny (0/1)
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM spring_accounts WHERE username = ?");

        // dla użytkownika zwraca info o uprawnieniach (rolach) danego użytkownika; wynik może składać się z wielu rekordów
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, role FROM spring_account_roles WHERE username = ?");
        return jdbcUserDetailsManager;
    }

    @Autowired
    private DataSource dataSource;
}


