package com.example.WikiCodia.controller;

import com.example.WikiCodia.model.Framework;
import com.example.WikiCodia.repository.FrameworkRepository;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/framework")
public class FrameworkController {

    @Autowired
	private FrameworkRepository frameworkRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Framework cree(@RequestBody Framework f) {
		frameworkRepository.save(f);
		return f;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Framework modification(@RequestBody Framework f) {

		Framework modifFramework = frameworkRepository.findById(f.getIdFramework()).get();

		modifFramework.setFramework(f.getFramework());
		modifFramework.setVersion(f.getVersion());

		frameworkRepository.save(modifFramework);

		return modifFramework;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		frameworkRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Framework voir(@PathVariable("id") Long id) {

		return frameworkRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Framework> voirTous() {

        return frameworkRepository.findAll().stream()
			     .distinct()
			     .collect(Collectors.toList()); 
    }
    
}
