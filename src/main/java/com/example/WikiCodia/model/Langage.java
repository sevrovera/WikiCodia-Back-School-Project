package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="langage")
public class Langage {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_lang")
    private long idLang;

    @Column(name="lang")
    private String lang;

    @Column(name="verstion")
    private String verstion;

    //GETTERS SETTERS
    public long getIdLang() {
        return idLang;
    }
    
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getVerstion() {
        return verstion;
    }

    public void setVerstion(String verstion) {
        this.verstion = verstion;
    }

    //CONSTRUCTOR
    public Langage(String lang, String verstion) {
        super();
        this.lang = lang;
        this.verstion = verstion;
    }
}