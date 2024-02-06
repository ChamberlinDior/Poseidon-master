package com.openclassroomsprojet.poseidon.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * * Cette classe permet de surveiller les requêtes effectuées sur les contrôleurs de l'application.
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j
@Component
public class CustomRequestLoggingFilter extends GenericFilterBean {

    /**
     * Lorsqu'une requête est effectuée, enregistre la requête, puis enregistre la réponse.
     *
     * @param servletRequest  Contient les informations de la requête du client
     * @param servletResponse Contient la réponse de la requête
     * @param chain           Chaîne d'invocation d'une requête filtrée
     * @throws IOException      Signale une exception d'entrée/sortie
     * @throws ServletException Signale une exception de servlet HTML
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest currentRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse currentResponse = (HttpServletResponse) servletResponse;
        StringBuffer requestURL = currentRequest.getRequestURL();
        log.info("Request URL: {}", requestURL);
        try {
            chain.doFilter(currentRequest, servletResponse);
        } finally {
            int status = currentResponse.getStatus();
            log.info("Response status: {}", status);
        }
    }
}