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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.WikiCodia.model.Type;
import com.example.WikiCodia.repository.TypeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	TypeRepository typeRepository;
	
	@GetMapping("/types/all")
	  public ResponseEntity<List<Type>> getAllTypes(@RequestParam(required = false) String libType) {
	    try {
	      List<Type> types = new ArrayList<Type>();

	      if (libType == null)
	        typeRepository.findAll().forEach(types::add);
	      else
	        typeRepository.findByLibTypeContaining(libType).forEach(types::add);

	      if (types.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(types, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @GetMapping("/types/{id}")
	  public ResponseEntity<Type> getTypeById(@PathVariable("id") long id) {
	    Optional<Type> typeData = typeRepository.findById(id);

	    if (typeData.isPresent()) {
	      return new ResponseEntity<>(typeData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @PostMapping("/types")
	  public ResponseEntity<Type> createType(@RequestBody Type type) {
	    try {
	      Type _type = typeRepository.save(new Type(type.getLibType()));
	      return new ResponseEntity<>(_type, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	  }

	  @PutMapping("/types/{id}")
	  public ResponseEntity<Type> updateType(@PathVariable("id") long id, @RequestBody Type typeUpdated) {
	    Optional<Type> typeData = typeRepository.findById(id);
	    
	    if (typeData.isPresent()) {
	      Type _type = typeData.get();
	      if (typeUpdated.getLibType() != null) {
	    	  _type.setLibType(typeUpdated.getLibType());
	      }
	      return new ResponseEntity<>(typeRepository.save(_type), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @DeleteMapping("/types/{id}")
	  public ResponseEntity<HttpStatus> deleteType(@PathVariable("id") long id) {
	    try {
	      typeRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	  }

	  @DeleteMapping("/types")
	  public ResponseEntity<HttpStatus> deleteAllTypes() {
	    try {
	      typeRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }

	  }

}
