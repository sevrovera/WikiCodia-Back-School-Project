package com.example.WikiCodia.controller;

import com.example.WikiCodia.model.Langage;
import com.example.WikiCodia.repository.LangageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/langage")
public class LangageController {

    @Autowired
	private LangageRepository langageRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Langage cree(@RequestBody Langage l) {
		langageRepository.save(l);
		return l;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Langage modification(@RequestBody Langage l) {

		Langage modifLangage = langageRepository.findById(l.getIdLang()).get();

		modifLangage.setLang(l.getLang());
		modifLangage.setVerstion(l.getVerstion());

		langageRepository.save(modifLangage);

		return modifLangage;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		langageRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Langage voir(@PathVariable("id") Long id) {

		return langageRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Langage> voirTous() {

        return langageRepository.findAll();
        
    }
}
