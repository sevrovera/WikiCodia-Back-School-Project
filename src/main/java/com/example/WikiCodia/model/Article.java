package com.example.WikiCodia.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "article")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_article",unique = true, nullable = false)
	private Long idArticle;

	@NotNull
	@Column(name = "titre")
	private String titre;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "contenu")
	private String contenu;
	
	@Column(name = "date_creation")
	private LocalDate dateCreation;
	
	@Column(name = "date_derniere_modif")
	private LocalDate dateDerniereModif;
	
	@Column(name = "est_publie", columnDefinition = "boolean default false")
	private Boolean estPublie;

	@Column(name = "est_promu", columnDefinition = "boolean default false")
	private Boolean estPromu;
	
	@Column(name = "est_valide", columnDefinition = "boolean default false")
	private Boolean estValide;

	@OneToMany
	private List<Vote> vote;
	
	// En partant du principe qu'un article peut être associé à plusieurs langages ou framework (par ex un article comparatif) :
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Langage> langage;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Framework> framework;
	
	@ManyToOne
    @JoinColumn(name ="id_utilisateur")
	private Utilisateur auteur;
	
	@ManyToOne
    @JoinColumn(name ="id_type")
	private Type type;
	
	@ManyToOne
    @JoinColumn(name ="id_categorie")
	private Categorie categorie;
	
	
	public Article() {
	}
	
	public Article(Long idArticle, @NotNull String titre, String description, String contenu, LocalDate dateCreation,
			LocalDate dateDerniereModif, Boolean estPublie, Boolean estPromu, Boolean estValide, List<Vote> vote, List<Langage> langage,
			List<Framework> framework, Utilisateur auteur, Type type, Categorie categorie) {
		super();
		this.idArticle = idArticle;
		this.titre = titre;
		this.description = description;
		this.contenu = contenu;
		this.dateCreation = dateCreation;
		this.dateDerniereModif = dateDerniereModif;
		this.estPublie = estPublie;
		this.estPromu = estPromu;
		this.estValide = estValide;
		this.vote = vote;
		this.langage = langage;
		this.framework = framework;
		this.auteur = auteur;
		this.type = type;
		this.categorie = categorie;
	}



	public Long getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(Long idArticle) {
		this.idArticle = idArticle;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getDateDerniereModif() {
		return dateDerniereModif;
	}

	public void setDateDerniereModif(LocalDate dateDerniereModif) {
		this.dateDerniereModif = dateDerniereModif;
	}

	public Boolean getEstPublie() {
		return estPublie;
	}

	public void setEstPublie(Boolean estPublie) {
		this.estPublie = estPublie;
	}

	public Boolean getEstPromu() {
		return estPromu;
	}

	public void setEstPromu(Boolean estPromu) {
		this.estPromu = estPromu;
	}
	
	public Boolean getEstValide() {
		return estValide;
	}

	public void setEstValide(Boolean estValide) {
		this.estValide = estValide;
	}

	public List<Vote> getVote() {
		return vote;
	}
	
	public void setVote(List<Vote> vote) {
		this.vote = vote;
	}
	
	public List<Langage> getLangage() {
		return langage;
	}

	public void setLangage(List<Langage> langage) {
		this.langage = langage;
	}

	public List<Framework> getFramework() {
		return framework;
	}

	public void setFramework(List<Framework> framework) {
		this.framework = framework;
	}

	public Utilisateur getAuteur() {
		return auteur;
	}

	public void setAuteur(Utilisateur auteur) {
		this.auteur = auteur;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	
}
