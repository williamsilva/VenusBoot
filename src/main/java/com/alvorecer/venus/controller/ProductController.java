package com.alvorecer.venus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.controller.page.PageWrapper;
import com.alvorecer.venus.model.Product;
import com.alvorecer.venus.repository.Products;
import com.alvorecer.venus.repository.filter.ProductFilter;
import com.alvorecer.venus.service.CadasterProductsService;
import com.alvorecer.venus.service.excepition.ClientJaCadastradoExcepition;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private Products products;

	@Autowired
	private CadasterProductsService cadasterProductsService;

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Product> list(String name) {
		return products.porNameEAtivo(name);
	}

	@GetMapping
	public ModelAndView list(ProductFilter productFilter, BindingResult result,
			@PageableDefault(size = 8) Pageable pageable, HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("formSystem/products/ProductsList");

		PageWrapper<Product> pageWrapper = new PageWrapper<>(products.filter(productFilter, pageable),
				httpServletRequest);
		modelAndView.addObject("page", pageWrapper);

		return modelAndView;
	}

	@RequestMapping("/new")
	public ModelAndView news(Product product) {
		ModelAndView modelAndView = new ModelAndView("formSystem/products/ProductsRegister");

		return modelAndView;
	}

	@PostMapping({ "/new", "{\\+d}" })
	private ModelAndView save(@Valid Product product, BindingResult result, Model model,
			RedirectAttributes attributes) {
				
		if (result.hasErrors()) {
			return news(product);
		}

		try {
			cadasterProductsService.save(product);
		} catch (ClientJaCadastradoExcepition e) {
			result.rejectValue("name", e.getMessage(), e.getMessage());
			return news(product);
		}

		attributes.addFlashAttribute("message", "Produto Salvo com Sucesso!");
		return new ModelAndView("redirect:/products/new");
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Product product) {
		ModelAndView modelAndView = news(product);
		modelAndView.addObject(product);

		return modelAndView;
	}

}
