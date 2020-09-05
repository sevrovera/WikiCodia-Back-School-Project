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
	@Column(name="id_lang",unique = true, nullable = false)
    private long idLang;
    
    
    @Column(name="lang")
    private String lang;

    @Column(name="version")
    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    //CONSTRUCTOR
    public Langage(String lang, String version) {
        super();
        this.lang = lang;
        this.version = version;
    }
    
    public Langage() {

    }
}