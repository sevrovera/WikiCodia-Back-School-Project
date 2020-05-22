package com.example.WikiCodia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_role")
    private long id_role;

    @Column(name="role")
    private String role;

    //GETTERS SETTERS
    public long getId_role() {
        return id_role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //CONSTRUCTOR
    public Role(String role) {
        super();
        this.role = role;
    }
}
