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
	@Column(name="id_framework",unique = true, nullable = false)
    private long idFramework;

    @Column(name="framework")
    private String framework;

    @Column(name="version")
    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    //CONSTRUCTOR
    public Framework(String framework, String version) {
        super();
        this.framework = framework;
        this.version = version;
    }
    
    public Framework() {
    }

}
