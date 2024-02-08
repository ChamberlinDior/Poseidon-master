package com.openclassroomsprojet.poseidon.configuration.security;

import com.openclassroomsprojet.poseidon.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

/**
 * Cette classe permet de configurer et d'initialiser Spring Security
 * @author chamberlin dior
 * @version 1.0
 */
@Configuration // Indiquer que cette classe est une classe de configuration
@EnableWebSecurity // Activer la sécurité web avec Spring Security
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // Injecter les dépendances automatiquement
    private MyUserDetailsService myUserDetailsService; // Déclarer le service personnalisé pour gérer les détails des utilisateurs
    @Autowired // Injecter les dépendances automatiquement
    private DataSource dataSource; // Déclarer la source de données pour la connexion à la base de données

    @Override // Redéfinir la méthode de la classe parente
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(myUserDetailsService) // Utiliser le service personnalisé pour l'authentification des utilisateurs
                .passwordEncoder(passwordEncoder()) // Utiliser le codeur de mot de passe pour hasher les mots de passe
                .and() // Ajouter un autre fournisseur d'authentification
                .authenticationProvider(authenticationProvider()) // Utiliser le fournisseur d'authentification personnalisé
                .jdbcAuthentication() // Utiliser l'authentification basée sur JDBC
                .dataSource(dataSource); // Utiliser la source de données injectée
    }

    @Override // Redéfinir la méthode de la classe parente
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = httpSecurity
                .csrf() // Activer la protection contre les attaques CSRF
                .disable() // Désactiver la protection CSRF
                .authorizeRequests() // Configurer les autorisations des requêtes HTTP
                .antMatchers("/", "/login", "/error/**", "/css/**").permitAll() // Autoriser l'accès aux pages d'accueil, de connexion, d'erreur et aux fichiers CSS

                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("USER")


                .anyRequest() // Pour toute autre requête
                .authenticated() // Exiger l'authentification de l'utilisateur
                .and() // Ajouter une autre configuration
                .oauth2Login().defaultSuccessUrl("/bidList/list") // Utiliser l'authentification OAuth2 et rediriger vers la liste des offres en cas de succès
                .and() // Ajouter une autre configuration
                .formLogin() // Utiliser le formulaire de connexion par défaut
                .defaultSuccessUrl("/bidList/list") // Rediriger vers la liste des offres en cas de succès
                .permitAll() // Autoriser l'accès au formulaire de connexion à tous
                .and()


                // Ajouter une autre configuration
                .logout() // Configurer la déconnexion
                .logoutUrl("/app-logout") // Définir l'URL de déconnexion
                .invalidateHttpSession(true) // Invalider la session HTTP
                .logoutSuccessUrl("/") // Rediriger vers la page d'accueil en cas de succès
                .deleteCookies("JSESSIONID") // Supprimer le cookie de session
                .and() // Ajouter une autre configuration
                .rememberMe().key("uniqueAndSecret") // Activer la fonctionnalité "se souvenir de moi" avec une clé secrète
                .and().exceptionHandling() // Gérer les exceptions
                .accessDeniedPage("/app/error");// Rediriger vers la page d'erreur en cas d'accès refusé

    }

    @Bean // Indiquer que cette méthode renvoie un bean géré par Spring
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Renvoyer une instance de BCryptPasswordEncoder
    }

    @Bean // Indiquer que cette méthode renvoie un bean géré par Spring
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Créer une instance de DaoAuthenticationProvider
        authProvider.setUserDetailsService(myUserDetailsService); // Utiliser le service personnalisé pour gérer les détails des utilisateurs
        authProvider.setPasswordEncoder(passwordEncoder()); // Utiliser le codeur de mot de passe pour hasher les mots de passe
        return authProvider; // Renvoyer le fournisseur d'authentification personnalisé
    }
}
