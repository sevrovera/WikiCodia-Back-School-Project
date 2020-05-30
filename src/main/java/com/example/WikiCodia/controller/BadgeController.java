package com.example.WikiCodia.controller;

import com.example.WikiCodia.model.Badge;
import com.example.WikiCodia.repository.BadgeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/badge")

public class BadgeController {

    @Autowired
    private BadgeRepository badgeRepository;
    
    //TODO: gerer ca quand on auras decidé de la class badge 

	/*@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Badge cree(@RequestBody Badge b) {
		badgeRepository.save(b);
		return l;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Badge modification(@RequestBody Badge b) {

		Badge modifBadge = badgeRepository.findById(b.getIdBadge()).get();

		modifBadge.setLang(b.getBadge());

		badgeRepository.save(modifBadge);

		return modifBadge;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		badgeRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Badge voir(@PathVariable("id") Long id) {

		return badgeRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Badge> voirTous() {

        return badgeRepository.findAll();
        
    }*/

}
