package com.alvorecer.venus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.model.enun.TypeClient;
import com.alvorecer.venus.service.CadasterClientService;
import com.alvorecer.venus.service.excepition.ClientJaCadastradoExcepition;

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private CadasterClientService cadasterClientService;
	

	@RequestMapping("/new")
	public ModelAndView news(Client client) {
		ModelAndView andView = new ModelAndView("client/ClientRegister");
		andView.addObject("type", TypeClient.values());

		return andView;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	private ModelAndView cadaster(@Valid Client client, BindingResult result, Model model,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return news(client);
		}
		
		try {
			cadasterClientService.save(client);
		} catch (ClientJaCadastradoExcepition e) {
			result.rejectValue("name", e.getMessage(), e.getMessage());
		}
		
		attributes.addFlashAttribute("message", "Cliente Salvo com Sucesso!");
		return new ModelAndView("redirect:/clients/new");
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> save(@RequestBody @Valid Client client, BindingResult result) {
		
		if (result.hasErrors()) {
			if(result.hasFieldErrors("typeClient")){
				return ResponseEntity.badRequest().body(result.getFieldError("typeClient").getDefaultMessage());
			}
			
			if(result.hasFieldErrors("cpfOuCnpj")){
				return ResponseEntity.badRequest().body(result.getFieldError("cpfOuCnpj").getDefaultMessage());
			}		
			
			if(result.hasFieldErrors("name")){
				return ResponseEntity.badRequest().body(result.getFieldError("name").getDefaultMessage());
			}
			
			if(result.hasFieldErrors("email")){
				return ResponseEntity.badRequest().body(result.getFieldError("email").getDefaultMessage());
			}
			
			if(result.hasFieldErrors("city")){
				return ResponseEntity.badRequest().body(result.getFieldError("city").getDefaultMessage());
			}			
		}

		try {
			client = cadasterClientService.save(client);
		} catch (ClientJaCadastradoExcepition e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok(client);
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> list (Client client){

		return ResponseEntity.ok(cadasterClientService.getClient(client));
	}	
}
