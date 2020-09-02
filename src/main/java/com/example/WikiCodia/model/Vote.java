	package com.example.WikiCodia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Embeddable
public class Vote implements Serializable {
    
//    @Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id_vote", unique = true, nullable = false)
//    private Long idVote;
    
	private static final long serialVersionUID = 6821141747211634995L;

	@NotNull
    private Boolean liked = false;
    
    @NotNull
    private Utilisateur utilisateur;

    private String commentaire;
    
    
    public Vote() {
    	
    }

    

	public Vote(@NotNull Boolean liked, @NotNull Utilisateur utilisateur, String commentaire) {
		super();
		this.liked = liked;
		this.utilisateur = utilisateur;
		this.commentaire = commentaire;
	}



	public Boolean getLiked() {
		return liked;
	}


	public void setLiked(Boolean liked) {
		this.liked = liked;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



    

}