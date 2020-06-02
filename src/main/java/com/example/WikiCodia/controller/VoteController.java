package com.example.WikiCodia.controller;

import com.example.WikiCodia.model.Vote;
import com.example.WikiCodia.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vote")
public class VoteController {
	
    @Autowired
	private VoteRepository voteRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Vote cree(@RequestBody Vote v) {
		voteRepository.save(v);
		return v;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Vote modification(@RequestBody Vote v) {

		Vote modifVote = voteRepository.findById(v.getIdVote()).get();

		modifVote.setLiked(v.getLiked());
		modifVote.setUtilisateur(v.getUtilisateur());
		modifVote.setCommentaire(v.getCommentaire());

		voteRepository.save(modifVote);

		return modifVote;
	}

	@RequestMapping(value = "/supression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean supression(@PathVariable("id") Long id) {
		voteRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Vote voir(@PathVariable("id") Long id) {

		return voteRepository.findById(id).get();

    }
    
    @RequestMapping("/all")
	@ResponseBody
	public Iterable<Vote> voirTous() {

        return voteRepository.findAll();
        
    }

}