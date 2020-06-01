package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
public class Vote {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vote")
    private long idVote;

    @Column(name="like")
    private Boolean like;

    @Column(name="utilisateur")
    private Utilisateur utilisateur;

    @Column(name="commentaire")
    private String commentaire;

    public long getIdVote() {
        return idVote;
    }
    
    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
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