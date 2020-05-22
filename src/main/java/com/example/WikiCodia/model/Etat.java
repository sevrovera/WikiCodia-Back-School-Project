package com.example.WikiCodia.model;

@Entity
@Table(name="etat")
public class Etat {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_etat")
    private long id_etat;

    @Column(name="etat")
    private String etat;
}
