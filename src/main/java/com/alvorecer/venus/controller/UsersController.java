package com.alvorecer.venus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.controller.page.PageWrapper;
import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.model.enun.StatusUsers;
import com.alvorecer.venus.repository.Groups;
import com.alvorecer.venus.repository.Users;
import com.alvorecer.venus.repository.filter.UserFilter;
import com.alvorecer.venus.service.CadasterUserService;
import com.alvorecer.venus.service.excepition.SenhaObrigatorioException;
import com.alvorecer.venus.service.excepition.UserRegisterException;

@Controller
@RequestMapping("/users")
public class UsersController {

	private String BaseURL = "formSystem/users/";
	
	@Autowired
	private Groups groups;
	
	@Autowired
	private Users users;

	@Autowired
	private CadasterUserService registerUserService;
	
	@GetMapping("perfil")
	public ModelAndView contasPagar() {

		ModelAndView andView = new ModelAndView(BaseURL +"Usuario");

		return andView;
	}
	
	
	@GetMapping
	public ModelAndView list(UserFilter userFilter, BindingResult result,
			@PageableDefault(size = 8) Pageable pageable, HttpServletRequest httpServletRequest){
		
		ModelAndView modelAndView = new ModelAndView(BaseURL +"UserList");
		modelAndView.addObject("grupos", groups.findAll());
		
		PageWrapper<Use> pageWrapper = new PageWrapper<>(users.filter(userFilter, pageable),
				httpServletRequest);

		modelAndView.addObject("page", pageWrapper);
		
		return modelAndView;
	}

	@RequestMapping("/new")
	public ModelAndView news(Use use) {
		ModelAndView modelAndView = new ModelAndView(BaseURL +"UserRegister");
		modelAndView.addObject("groups", groups.findAll());

		return modelAndView;
	}

	@PostMapping({"/new","{\\+d}"})
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
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("id[]") Long[] id, @RequestParam("status") StatusUsers status) {		
		registerUserService.alterarStatus(id, status);
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Use use){
		ModelAndView modelAndView = news(use);
		modelAndView.addObject(use);
		
		return modelAndView;
	}
}
