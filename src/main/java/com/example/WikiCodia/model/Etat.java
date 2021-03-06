package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="etat")
public class Etat {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_etat", unique = true, nullable = false)
    private long idEtat;

    @Column(name="etat", unique = true, nullable = false)
    private String etat;

    //GETTERS SETTERS
	public long getIdEtat() {
		return idEtat;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
    }
    
    //CONSTRUCTOR
	public Etat(String etat) {
        super();
		this.etat = etat;
	}
	
	public Etat() {

	}
}
