package com.example.WikiCodia.repository;

import org.springframework.stereotype.Repository;

import com.example.WikiCodia.model.Framework;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FrameworkRepository extends JpaRepository<Framework , Long> {

	Framework findByFrameworkAndVerstionEquals(String framework, String verstion);

}
