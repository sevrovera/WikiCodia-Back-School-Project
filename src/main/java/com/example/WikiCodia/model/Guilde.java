package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="guilde")
public class Guilde {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_guilde")
    private long idGuilde;

    @Column(name="guilde")
    private String guilde;

    //GETTERS SETTERS
    public long getIdGuilde() {
        return idGuilde;
    }

    public String getGuilde() {
        return guilde;
    }

    public void setGuilde(String guilde) {
        this.guilde = guilde;
    }

    //CONSTRUCTOR
    public Guilde(String guilde) {
        super();
        this.guilde = guilde;
    }
}
