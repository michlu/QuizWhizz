package com.pw.quizwhizz.config;

import com.pw.quizwhizz.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * Klasa inicjalizujaca ustawienia Springa.
 * @author Michał Nowiński
 * @see AbstractAnnotationConfigDispatcherServletInitializer
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * @return zwraca ustawienia aplikacji
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, JpaConfig.class};
    }

    /**
     * @return zwraca ustawienia webowe aplikacji
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ThymeleafConfig.class};
    }

    /**
     * @return zwraca adres URL ktory ma byc obslugiwany przez springa
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * Rejestruje dodadkowe ustawienia
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }


    /**
     * @return ustawienia dla elementów MultiPartFile
     */
    private MultipartConfigElement getMultipartConfigElement() {
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement( LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }

    private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored
    private static final long MAX_FILE_SIZE = 2242880; // 5MB
    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB
    private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk

}