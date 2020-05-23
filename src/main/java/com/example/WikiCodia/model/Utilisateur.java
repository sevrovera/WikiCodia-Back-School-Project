package com.example.WikiCodia.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="utilisateur")
public class Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_utilisateur")
	private Long idUtilisateur;
	
	@Column(name="prenom")
	private String prenom;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="pseudo")
	private String pseudo;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="mot_de_passe")
	private String motDePasse;
	
	@Column(name="lien_linkedin")
	private String lienLinkedin;
	
	@Column(name="statut")
	private String statut;
	
	@Column(name="date_inscription")
	private LocalDate dateInscription;
	
	@Column(name="date_derniere_connexion")
	private LocalDate dateDerniereConnexion;

	@ManyToOne
	private Etat etat;
	
	@ManyToOne
	private Role role;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Guilde> guilde;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Framework> framework;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Langage> langage;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Type> type;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Categorie> categorie;
	
	//GETTERS SETTERS
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLienLinkedIn() {
		return lienLinkedin;
	}

	public void setLienLinkedIn(String lienLinkedin) {
		this.lienLinkedin = lienLinkedin;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	public LocalDate getDateDerniereConnexion() {
		return dateDerniereConnexion;
	}

	public void setDateDerniereConnexion(LocalDate dateDerniereConnexion) {
		this.dateDerniereConnexion = dateDerniereConnexion;
	}

	
	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getLienLinkedin() {
		return lienLinkedin;
	}

	public void setLienLinkedin(String lienLinkedin) {
		this.lienLinkedin = lienLinkedin;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Guilde> getGuilde() {
		return guilde;
	}

	public void setGuilde(List<Guilde> guilde) {
		this.guilde = guilde;
	}

	public List<Framework> getFramework() {
		return framework;
	}

	public void setFramework(List<Framework> framework) {
		this.framework = framework;
	}

	public List<Langage> getLangage() {
		return langage;
	}

	public void setLangage(List<Langage> langage) {
		this.langage = langage;
	}

	public List<Type> getType() {
		return type;
	}

	public void setType(List<Type> type) {
		this.type = type;
	}

	public List<Categorie> getCategorie() {
		return categorie;
	}

	public void setCategorie(List<Categorie> categorie) {
		this.categorie = categorie;
	}		
	
	//CONSTRUCTOR	
	/*
	public Utilisateur(String prenom, String nom, String pseudo, String mail,
			String lienLinkedin, String statut, LocalDate dateDerniereConnexion) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.pseudo = pseudo;
		this.mail = mail;
		this.lienLinkedin = lienLinkedin;
		this.statut = statut;
		this.dateDerniereConnexion = dateDerniereConnexion;
	}
	
	public Utilisateur() {
		
	}*/

	public Utilisateur(String prenom, String nom, String pseudo, String mail, String motDePasse, String lienLinkedin,
			String statut, LocalDate dateInscription, LocalDate dateDerniereConnexion, Etat etat, Role role,
			List<Guilde> guilde, List<Framework> framework, List<Langage> langage, List<Type> type,
			List<Categorie> categorie) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.pseudo = pseudo;
		this.mail = mail;
		this.motDePasse = motDePasse;
		this.lienLinkedin = lienLinkedin;
		this.statut = statut;
		this.dateInscription = dateInscription;
		this.dateDerniereConnexion = dateDerniereConnexion;
		this.etat = etat;
		this.role = role;
		this.guilde = guilde;
		this.framework = framework;
		this.langage = langage;
		this.type = type;
		this.categorie = categorie;
	}
}
