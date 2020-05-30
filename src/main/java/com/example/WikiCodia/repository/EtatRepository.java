package com.example.WikiCodia.repository;

import org.springframework.stereotype.Repository;

import com.example.WikiCodia.model.Etat;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EtatRepository extends JpaRepository<Etat, Long>{

}
