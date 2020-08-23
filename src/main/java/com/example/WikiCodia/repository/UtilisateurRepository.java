package com.example.WikiCodia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import com.example.WikiCodia.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur , Long>{

    // Concerne Spring Security
    @Query(" select u from Utilisateur u " +
    " where u.mail = ?1")
    Optional<Utilisateur> findUserWithMail(String mail);
    
    Utilisateur findByMailAndPseudoEquals (String mail, String pseudo);
    Utilisateur findByIdUtilisateurEquals (Long idUtilisateur);

}
