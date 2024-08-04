package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import tacos.User;
import tacos.data.UserRepository;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Переписал конфигурацию безопасности как в репозитории книги,
        // чтобы работали POST запросы
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/ingredients").permitAll()
                        .requestMatchers("/**").permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login"))
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
//         http
//                 .authorizeHttpRequests(authz -> authz
//                     .requestMatchers("/design", "/orders").hasRole("USER")
//                         .requestMatchers("/", "/**", "/api/**").permitAll()
//                     .anyRequest().authenticated())
//                 .oauth2Login((oauth2) -> oauth2
//                         .loginPage("/login")
//                         .userInfoEndpoint(userInfo -> userInfo
//                                 .oidcUserService(oidcUserService())))
//                 .formLogin(formLogin -> formLogin
//                         .loginPage("/login")
//                         .defaultSuccessUrl("/design"))
//                 .logout(LogoutConfigurer::permitAll);
//         return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }
}
