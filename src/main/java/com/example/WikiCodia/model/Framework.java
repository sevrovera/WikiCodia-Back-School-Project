package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="framework") 
public class Framework {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_framework")
    private long idFramework;

    @Column(name="framework")
    private String framework;

    @Column(name="verstion")
    private String verstion;

    //GETTERS SETTERS
    public long getIdFramework() {
        return idFramework;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getVerstion() {
        return verstion;
    }

    public void setVerstion(String verstion) {
        this.verstion = verstion;
    }

    //CONSTRUCTOR
    public Framework(String framework, String verstion) {
        super();
        this.framework = framework;
        this.verstion = verstion;
    }

}
