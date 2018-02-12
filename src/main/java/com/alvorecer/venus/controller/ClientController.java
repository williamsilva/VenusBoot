package com.alvorecer.venus.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.controller.page.PageWrapper;
import com.alvorecer.venus.controller.validator.ClienteValidator;
import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.model.enun.TypeClientEnun;
import com.alvorecer.venus.repository.Clients;
import com.alvorecer.venus.repository.States;
import com.alvorecer.venus.repository.filter.ClientFilter;
import com.alvorecer.venus.service.CadasterClientService;
import com.alvorecer.venus.service.excepition.ClientJaCadastradoExcepition;
import com.alvorecer.venus.service.excepition.RegistroObrigatorioException;

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private CadasterClientService cadasterClientService;

	@Autowired
	private States states;

	@Autowired
	private Clients clients;

	@Autowired
	private ClienteValidator clientValidator;

	@GetMapping
	public ModelAndView list(ClientFilter clientFilter, BindingResult result,
			@PageableDefault(size = 8) Pageable pageable, HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("formSystem/clients/ClientList");
		modelAndView.addObject("type", TypeClientEnun.values());
		modelAndView.addObject("states", states.findAll());

		PageWrapper<Client> pageWrapper = new PageWrapper<>(clients.filter(clientFilter, pageable), httpServletRequest);
		modelAndView.addObject("page", pageWrapper);

		return modelAndView;
	}

	@RequestMapping("/new")
	public ModelAndView news(Client client) {
		ModelAndView modelAndView = new ModelAndView("formSystem/clients/ClientRegister");
		modelAndView.addObject("type", TypeClientEnun.values());
		modelAndView.addObject("states", states.findAll());

		return modelAndView;
	}

	@PostMapping({ "/new", "{\\+d}" })
	private ModelAndView save(@Valid Client client, BindingResult result, Model model, RedirectAttributes attributes) {

		clientValidator.validate(client, result);

		if (result.hasErrors()) {
			return news(client);
		}

		try {
			cadasterClientService.save(client);
		} catch (ClientJaCadastradoExcepition e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return news(client);
		} catch (RegistroObrigatorioException e) {
			result.rejectValue("city", e.getMessage(), e.getMessage());
			return news(client);
		}

		attributes.addFlashAttribute("message", "Cliente Salvo com Sucesso!");
		return new ModelAndView("redirect:/clients/new");
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> save(@RequestBody @Valid Client client, BindingResult result) {

		if (result.hasErrors()) {
			if (result.hasFieldErrors("typeClient")) {
				return ResponseEntity.badRequest().body(result.getFieldError("typeClient").getDefaultMessage());
			}

			if (result.hasFieldErrors("cpfOuCnpj")) {
				return ResponseEntity.badRequest().body(result.getFieldError("cpfOuCnpj").getDefaultMessage());
			}

			if (result.hasFieldErrors("name")) {
				return ResponseEntity.badRequest().body(result.getFieldError("name").getDefaultMessage());
			}

			if (result.hasFieldErrors("email")) {
				return ResponseEntity.badRequest().body(result.getFieldError("email").getDefaultMessage());
			}

			if (result.hasFieldErrors("city")) {
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

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Client client) {
		ModelAndView modelAndView = news(client);
		modelAndView.addObject(client);

		return modelAndView;
	}

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Client> pesquisar(String name) {
		validName(name);
		return clients.findByNameStartingWithIgnoreCase(name);
	}

	@RequestMapping(value = "/cpfOuCnpj", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> list(Client client) {

		Optional<Client> clientOptional = clients.findByCpfOuCnpj(client.getCpfOuCnpj());
		if (clientOptional.isPresent()) {
			return ResponseEntity.ok(clientOptional.get());
		}
		return ResponseEntity.ok(new Client());
	}

	private void validName(String name) {
		if (StringUtils.isEmpty(name) || name.length() < 3) {
			throw new IllegalArgumentException();
		}
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}

}
