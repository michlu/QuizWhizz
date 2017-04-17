package com.pw.quizwhizz.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * klasa filtra tworzy obiekt DelegatingFilterProxy - łącznik pomiędzy kontekstem serwletów, a kontekstem Springa
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
