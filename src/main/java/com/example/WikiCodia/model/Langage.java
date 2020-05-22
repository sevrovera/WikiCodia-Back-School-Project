package com.example.WikiCodia.model;

@Entity
@Table(name="langage")
public class Langage {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_lang")
    private long id_lang;

    @Column(name="lang")
    private String lang;

    @Column(name="verstion")
    private String verstion;
}