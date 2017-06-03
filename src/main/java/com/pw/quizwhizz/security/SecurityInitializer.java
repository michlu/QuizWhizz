package com.pw.quizwhizz.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Klasa filtra. Tworzy obiekt DelegatingFilterProxy - łącznik pomiędzy kontekstem serwletów, a kontekstem Springa
 * @author Michał Nowiński
 * @see AbstractSecurityWebApplicationInitializer
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
