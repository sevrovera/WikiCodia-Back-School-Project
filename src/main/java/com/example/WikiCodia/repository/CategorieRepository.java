package com.example.WikiCodia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WikiCodia.model.Categorie;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {
	List<Categorie> findByLibCategorieContaining(String libCategorie);
	
	Categorie findByLibCategorieEquals(String libCategorie);
}
