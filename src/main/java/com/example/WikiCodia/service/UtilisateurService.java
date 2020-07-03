package com.example.WikiCodia.service;

import java.util.Objects;

import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

// Concerne Spring Security
@Service
@Slf4j
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository userRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Objects.requireNonNull(mail);
        Utilisateur user = userRepository.findUserWithMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }
}
