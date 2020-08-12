package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vote")
public class Vote {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vote", unique = true, nullable = false)
    private Long idVote;
    
    @Column(name = "liked", columnDefinition = "boolean default false")
    private Boolean liked;
    
    @NotNull
	@ManyToOne
    @JoinColumn(name ="id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name="commentaire")
    private String commentaire;

    
    public Vote() {
    	
    }
    
    public Vote(Long idVote, Boolean liked, @NotNull Utilisateur utilisateur, String commentaire) {
		super();
		this.idVote = idVote;
		this.liked = liked;
		this.utilisateur = utilisateur;
		this.commentaire = commentaire;
	}
	
	public Long getIdVote() {
        return idVote;
    }
    
	public void setIdVote(Long idVote) {
        this.idVote = idVote;
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