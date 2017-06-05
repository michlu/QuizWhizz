package com.pw.quizwhizz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * Klasa konfiguracji SpringSecurity. Metoda {@link SecurityConfig#configure} ustawiajaca zabezpiecznia dostepow
 * @author Michał Nowiński
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity // tworzy filtr springSecurityFilterChain do którego delegowane są żądania przez DelegatingFilterProxy
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Implementacja interfeujsu UserDetrailsService, sluzy do pobierania danych o uzytkowniku.
     * Jego zadaniem jest stworzenie obiektu UserDetails opakowującego inofmacje o uzytkowniku
     * takie jak nazwa uzytkownika, haslo, rola itp.
     */
    @Bean
    public UserDetailsService userDetailsServiceImpl() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Bean udostępniajacy algorytm BCrypt wykorzystywany do hashowania hasla
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean wspomagajacy framework Thymeleafe oraz Spring Security
     * thymeleaf-extras-springsecurity4
     */
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    /**
     * Bean wymagany do szyfrowania hasla oraz autologowania.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Metoda ustawiajaca zabezpieczenia na żądania pod danymi URL'ami.
     * @param http HttpSecurity wymagany do nakladania zabezpieczen
     * @throws Exception wymagane przez HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http
                .addFilterBefore(filter,CsrfFilter.class)   // ustawia kodowanie na UTF-8
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/app/**").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/resources/**").permitAll().anyRequest().permitAll()
                    .antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") //dostep dla wyszstkch uzytkownikow
                    .antMatchers("/game/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") //dostep dla wyszstkch uzytkownikow
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //dostep tylko dla adminow
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/loginform")
                        .permitAll()
                    .loginProcessingUrl("/processlogin")
                        .permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/loginerror")
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                        .permitAll();
    }
}