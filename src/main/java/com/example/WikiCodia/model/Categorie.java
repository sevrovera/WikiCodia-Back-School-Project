package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categorie")
public class Categorie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_categorie",unique = true, nullable = false)
	private Long idCategorie;
	
	@NotNull
	@Column(name = "lib_categorie",unique = true, nullable = false)
	private String libCategorie;

	public Categorie() {
		
	}
	
	public Categorie(@NotNull String libCategorie) {
		super();
		this.libCategorie = libCategorie;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getLibCategorie() {
		return libCategorie;
	}

	public void setLibCategorie(String libCategorie) {
		this.libCategorie = libCategorie;
	}
	
	
}
