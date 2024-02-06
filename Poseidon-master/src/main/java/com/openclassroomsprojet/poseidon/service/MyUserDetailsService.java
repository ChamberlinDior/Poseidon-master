package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge les détails de l'utilisateur en utilisant le nom d'utilisateur fourni.
     *
     * @param nomUtilisateur Le nom d'utilisateur dont les détails doivent être chargés.
     * @return Les détails de l'utilisateur encapsulés dans un objet UserDetails.
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec le nom d'utilisateur fourni.
     */

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Recherche de l'utilisateur dans le référentiel (base de données) par son nom d'utilisateur
        User user = userRepository.findUserByUsername(username);
        // Vérifie si l'utilisateur est trouvé, sinon lance une exception
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // Retourne les détails de l'utilisateur encapsulés dans un objet UserDetails personnalisé
        return new MyUserDetails(user);
    }
}