package com.alvorecer.venus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.repository.Groups;
import com.alvorecer.venus.service.RegisterUserService;
import com.alvorecer.venus.service.excepition.SenhaObrigatorioException;
import com.alvorecer.venus.service.excepition.UserRegisterException;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Groups groups;

	@Autowired
	private RegisterUserService registerUserService;
	
	@GetMapping
	public ModelAndView list(){
		return new ModelAndView("users/Users");
	}

	@RequestMapping("/new")
	public ModelAndView news(Use use) {
		ModelAndView modelAndView = new ModelAndView("users/UserRegister");
		modelAndView.addObject("groups", groups.findAll());

		return modelAndView;
	}

	@PostMapping("/new")
	public ModelAndView salvar(@Valid Use use, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return news(use);
		}

		try {
			registerUserService.save(use);
		} catch (UserRegisterException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return news(use);
		} catch (SenhaObrigatorioException e) {
			result.rejectValue("password", e.getMessage(), e.getMessage());
			return news(use);
		}

		attributes.addFlashAttribute("message", "Usuário salvo com sucesso");
		return new ModelAndView("redirect:/users/new");
	}
}
