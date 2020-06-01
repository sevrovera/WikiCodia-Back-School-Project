package com.example.WikiCodia.controller;

import com.example.WikiCodia.model.Role;
import com.example.WikiCodia.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {
	
    @Autowired
	private RoleRepository roleRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Role cree(@RequestBody Role r) {
		roleRepository.save(r);
		return r;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Role modification(@RequestBody Role r) {

		Role modifRole = roleRepository.findById(r.getIdRole()).get();

		modifRole.setRole(r.getRole());

		roleRepository.save(modifRole);

		return modifRole;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		roleRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Role voir(@PathVariable("id") Long id) {

		return roleRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Role> voirTous() {

        return roleRepository.findAll();
        
    }
}
