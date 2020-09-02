package com.example.WikiCodia.repository;

import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	Vote findByLikedAndCommentaireAndUtilisateurEquals (Boolean liked, String commentaire, Utilisateur utilisateur);
    
}