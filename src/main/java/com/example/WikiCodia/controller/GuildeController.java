package com.example.WikiCodia.controller;

import com.example.WikiCodia.model.Guilde;
import com.example.WikiCodia.repository.GuildeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/guilde")
public class GuildeController {

    @Autowired
	private GuildeRepository guildeRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Guilde cree(@RequestBody Guilde g) {
		guildeRepository.save(g);
		return g;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Guilde modification(@RequestBody Guilde g) {

		Guilde modifGuilde = guildeRepository.findById(g.getIdGuilde()).get();

		modifGuilde.setGuilde(g.getGuilde());

		guildeRepository.save(modifGuilde);

		return modifGuilde;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		guildeRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Guilde voir(@PathVariable("id") Long id) {

		return guildeRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Guilde> voirTous() {

        return guildeRepository.findAll();
        
    }
}
