package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "type_article")
public class Type {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_type")
	private Long idType;
	
	@NotNull
	@Column(name = "lib_type")
	private String libType;

	public Type() {
		
	}
	
	public Type(@NotNull String libType) {
		super();
		this.libType = libType;
	}

	public Long getIdType() {
		return idType;
	}

	public void setIdType(Long idType) {
		this.idType = idType;
	}

	public String getLibType() {
		return libType;
	}

	public void setLibType(String libType) {
		this.libType = libType;
	}
	
	
	
	
	
}
