package com.alvorecer.venus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alvorecer.venus.model.State;
import com.alvorecer.venus.repository.States;

@Controller
@RequestMapping("/states")
public class StateController {
	
	@Autowired
	private States states;
	
	@Cacheable("states")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<State> getCodeSate() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		return states.findAll();
	}
}
