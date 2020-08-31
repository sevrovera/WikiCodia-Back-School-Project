package com.example.WikiCodia.repository;

import org.springframework.stereotype.Repository;

import com.example.WikiCodia.model.Langage;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LangageRepository extends JpaRepository<Langage , Long> {
	Langage findByLangEquals(String lang);
	Langage findByLangAndVersionEquals(String lang, String version);

	
}
