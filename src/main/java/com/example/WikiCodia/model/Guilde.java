package com.example.WikiCodia.model;

@Entity
@Table(name="guilde")
public class Guilde {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_guilde")
    private long id_guilde;

    @Column(name="guilde")
    private String guilde;
}
