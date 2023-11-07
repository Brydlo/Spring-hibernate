package sklep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain configHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
//   		 .csrf(authz -> authz.disable())
        ;

        return httpSecurity.build();
    }


    // Aspektem konfiguracji, który jest podawany w innej metodzie, jest zdefiniowany zbiór użytkowników.
    // W tej wersji definiujemy użytkowników w kodzie aplikacji ("in memory").
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails[] users = {
                User.withUsername("ala").password("{noop}ala123").roles("manager", "worker").build(),
                User.withUsername("ola").password("{noop}ola123").roles("worker").build(),
        };
        return new InMemoryUserDetailsManager(users);
    }


}


