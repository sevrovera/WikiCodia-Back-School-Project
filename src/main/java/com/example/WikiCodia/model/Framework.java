package com.example.WikiCodia.model;

@Entity
@Table(name="framework") 
public class Framework {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_framework")
    private long id_framework;

    @Column(name="framework")
    private String framework;

    @Column(name="verstion")
    private String verstion;

}
