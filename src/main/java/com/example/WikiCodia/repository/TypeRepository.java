package com.example.WikiCodia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WikiCodia.model.Type;

public interface TypeRepository extends JpaRepository<Type, Long>{
	List<Type> findByLibTypeContaining(String libType);

}
