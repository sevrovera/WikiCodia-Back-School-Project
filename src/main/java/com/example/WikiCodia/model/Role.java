package com.example.WikiCodia.model;

@Entity
@Table(name="role")
public class Role {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_role")
    private long id_role;

    @Column(name="role")
    private String role;
}
