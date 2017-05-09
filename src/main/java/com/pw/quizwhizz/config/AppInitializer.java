package com.pw.quizwhizz.config;

import com.pw.quizwhizz.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Klasa inicjalizujaca ustawienia Springa.
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, JpaConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ThymeleafConfig.class}; // konfuguracje webowe
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // adres URL ktory ma byc obslugiwany przez springa
    }
}