package com.example.WikiCodia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WikiCodia.model.Categorie;
import com.example.WikiCodia.model.Langage;
import com.example.WikiCodia.repository.CategorieRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/categories")
public class CategorieController {

	@Autowired
	CategorieRepository categorieRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Categorie>> getAllCategories(@RequestParam(required = false) String libCategorie) {
		try {
			List<Categorie> categories = new ArrayList<Categorie>();

			if (libCategorie == null)
				categorieRepository.findAll().forEach(categories::add);
			else
				categorieRepository.findByLibCategorieContaining(libCategorie).forEach(categories::add);

			if (categories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categorie> getCategorieById(@PathVariable("id") long id) {
		Optional<Categorie> categorieData = categorieRepository.findById(id);

		if (categorieData.isPresent()) {
			return new ResponseEntity<>(categorieData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/creation")
	@ResponseBody
	public Categorie createCategorie(@RequestBody Categorie cat) {
		categorieRepository.save(cat);
		return cat;
	}

	@PutMapping("/modification/{id}")
	public ResponseEntity<Categorie> updateCategorie(@PathVariable("id") long id,
			@RequestBody Categorie categorieUpdated) {
		Optional<Categorie> categorieData = categorieRepository.findById(id);

		if (categorieData.isPresent()) {
			Categorie _categorie = categorieData.get();
			if (categorieUpdated.getLibCategorie() != null) {
				_categorie.setLibCategorie(categorieUpdated.getLibCategorie());
			}
			return new ResponseEntity<>(categorieRepository.save(_categorie), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/suppression/{id}")
	public ResponseEntity<HttpStatus> deleteCategorie(@PathVariable("id") long id) {
		try {
			categorieRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/suppression/all")
	public ResponseEntity<HttpStatus> deleteAllCategories() {
		try {
			categorieRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
