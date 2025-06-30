package de.hsrm.mi.web.projekt.configuration;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import de.hsrm.mi.web.projekt.services.benutzer.BenutzerUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final BenutzerUserDetailsService benutzerUserDetailsService;

    public SecurityConfiguration(BenutzerUserDetailsService benutzerUserDetailsService) {
        this.benutzerUserDetailsService = benutzerUserDetailsService;
    }
    
    @Bean
    public SecurityFilterChain filterChainApp(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/assets/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/css/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/js/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers("/admin/ort/**").hasRole("CHEF")
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll())
            .formLogin(form -> form
                .defaultSuccessUrl("/admin/tour", true) 
                .permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/login"))
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(toH2Console())
                .ignoringRequestMatchers("/admin/benutzer//hx/feld/"))
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

     @Bean
    public UserDetailsService userDetailsService() {
        return benutzerUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(benutzerUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    
}
