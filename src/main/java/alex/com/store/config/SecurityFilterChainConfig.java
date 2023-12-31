package alex.com.store.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = false)
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/",
                                "/registration",
                                "/auth/**",
                                "/static/**",
                                "/images/**",
                                "/product/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form -> form.loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()))
                .logout(s -> s.logoutUrl("/auth/logout").logoutSuccessUrl("/").permitAll());
//
        return http.build();
    }

}
