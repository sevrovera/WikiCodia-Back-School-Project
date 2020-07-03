package com.example.WikiCodia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.WikiCodia.model.Etat;
import com.example.WikiCodia.repository.EtatRepository;

@Controller
@RequestMapping("/etat")
public class EtatController {

    @Autowired
	private EtatRepository etatRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Etat cree(@RequestBody Etat e) {
		etatRepository.save(e);
		return e;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Etat modification(@RequestBody Etat e) {

		Etat modifEtat = etatRepository.findById(e.getIdEtat()).get();

		modifEtat.setEtat(e.getEtat());

		etatRepository.save(modifEtat);

		return modifEtat;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		etatRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Etat voir(@PathVariable("id") Long id) {

		return etatRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Etat> voirTous() {

        return etatRepository.findAll();
        
	}

}
