package com.alvorecer.venus.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.model.City;
import com.alvorecer.venus.repository.Citys;

@Controller
@RequestMapping("/citys")
public class CityController {

	@Autowired
	private Citys citys;

	@Cacheable(value = "citys", key = "#codeState")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<City> getCodeSate(@RequestParam(name = "state", defaultValue = "-1") Long codeState) {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		return citys.findByStateId(codeState);
	}
	
	@PostMapping("/new")
	@CacheEvict(value = "citys" , key = "#city.state.id", condition = "#city.yesState()" )
	public ModelAndView salvar(@Valid City city, BindingResult result, RedirectAttributes attributes) {
		return new ModelAndView("redirect:/citys/new");
	}
}