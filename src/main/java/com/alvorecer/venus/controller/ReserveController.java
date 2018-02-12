package com.alvorecer.venus.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.Util;
import com.alvorecer.venus.controller.page.PageWrapper;
import com.alvorecer.venus.controller.validator.ReserveValidator;
import com.alvorecer.venus.mail.ReserveMailer;
import com.alvorecer.venus.model.ItemVoucher;
import com.alvorecer.venus.model.Product;
import com.alvorecer.venus.model.Reserve;
import com.alvorecer.venus.model.enun.StatusVouchers;
import com.alvorecer.venus.model.enun.TypeClientEnun;
import com.alvorecer.venus.repository.Products;
import com.alvorecer.venus.repository.Reserves;
import com.alvorecer.venus.repository.filter.ReserveFilter;
import com.alvorecer.venus.security.UserSystem;
import com.alvorecer.venus.service.CadasterReserveService;
import com.alvorecer.venus.session.TablesItensSession;

@Controller
@RequestMapping("/reservations")
public class ReserveController {

	private String BaseURL = "formSystem/reservations/";

	@Autowired
	private CadasterReserveService cadasterReserveServive;

	@Autowired
	private Products products;

	@Autowired
	private Reserves reserves;

	@Autowired
	private ReserveMailer mailer;

	@Autowired
	private TablesItensSession tablesItensSession;

	@Autowired
	private ReserveValidator reserveValidator;

	@InitBinder("reserve")
	public void initValidator(WebDataBinder binder) {
		binder.setValidator(reserveValidator);
	}

	@GetMapping
	public ModelAndView list(ReserveFilter reserveFilter, @PageableDefault(size = 8) Pageable pageable,
			HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView(BaseURL + "ReserveList");
		modelAndView.addObject("statusVouchers", StatusVouchers.values());
		modelAndView.addObject("type", TypeClientEnun.values());

		PageWrapper<Reserve> pageWrapper = new PageWrapper<>(reserves.filter(reserveFilter, pageable),
				httpServletRequest);

		modelAndView.addObject("page", pageWrapper);

		return modelAndView;
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Reserve reserve = reserves.buscarComItens(id);

		setUuid(reserve);
		for (ItemVoucher item : reserve.getItens()) {
			tablesItensSession.addItem(reserve.getUuid(), item.getProduct(), item.getAmount());
		}
		ModelAndView modelAndView = news(reserve);
		modelAndView.addObject(reserve);

		return modelAndView;
	}

	@GetMapping("new")
	public ModelAndView news(Reserve reserve) {
		ModelAndView modelAndView = new ModelAndView(BaseURL + "ReserveRegister");

		setUuid(reserve);

		modelAndView.addObject("statusVoucher", StatusVouchers.values());
		modelAndView.addObject("valorAntecipado", reserve.getValorAntecipado());
		modelAndView.addObject("valorTotalItens", tablesItensSession.getValorTotal(reserve.getUuid()));
		
		if(reserve.getVoucher() == null){
			reserve.setVoucher(Util.Voucher());
		}

		modelAndView.addObject("itens", reserve.getItens());

		return modelAndView;
	}

	@PostMapping("/item")
	public ModelAndView addItem(Long idProduct, String uuid) {
		Product product = products.findOne(idProduct);
		tablesItensSession.addItem(uuid, product, 1);

		return mvTabelaItensVoucher(uuid);
	}

	@PutMapping("/item/{idProduct}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("idProduct") Product product, Integer quantidade,
			String uuid) {

		tablesItensSession.alterarQuantidadeItens(uuid, product, quantidade);

		return mvTabelaItensVoucher(uuid);
	}

	@DeleteMapping("/item/{uuid}/{idProduct}")
	public ModelAndView excluirItem(@PathVariable("idProduct") Product product, @PathVariable String uuid) {
		tablesItensSession.excluirItem(uuid, product);
		return mvTabelaItensVoucher(uuid);
	}

	private ModelAndView mvTabelaItensVoucher(String uuid) {
		ModelAndView modelAndView = new ModelAndView(BaseURL + "ReserveTableItens");
		modelAndView.addObject("itens", tablesItensSession.getItens(uuid));
		modelAndView.addObject("valorTotal", tablesItensSession.getValorTotal(uuid));

		return modelAndView;
	}

	@PostMapping(value = "/new", params = "salvar")
	public ModelAndView salvar(Reserve reserve, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UserSystem userSystem) {

		validarReserve(reserve, result);
		if (result.hasErrors()) {
			return news(reserve);
		}

		reserve.setUse(userSystem.getUse());

		cadasterReserveServive.salvar(reserve);

		attributes.addFlashAttribute("message", "Reserva Salva com Sucesso!");
		return new ModelAndView("redirect:/reservations/new");
	}

	@PostMapping(value = "/new", params = "emitir")
	public ModelAndView emitir(Reserve reserve, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UserSystem userSystem) {

		validarReserve(reserve, result);
		if (result.hasErrors()) {
			return news(reserve);
		}

		reserve.setUse(userSystem.getUse());

		cadasterReserveServive.emitir(reserve);

		attributes.addFlashAttribute("message", "Reserva emitida com Sucesso!");
		return new ModelAndView("redirect:/reservations/new");
	}

	@PostMapping(value = "/new", params = "enviarEmail")
	public ModelAndView enviarEmail(Reserve reserve, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UserSystem userSystem) {

		validarReserve(reserve, result);
		if (result.hasErrors()) {
			return news(reserve);
		}

		reserve.setUse(userSystem.getUse());

		reserve = cadasterReserveServive.salvar(reserve);
		mailer.enviar(reserve);

		attributes.addFlashAttribute("message", "Reserva Salva e E-mail enviado!");
		return new ModelAndView("redirect:/reservations/new");
	}

	@PostMapping(value = "/new", params = "cancelar")
	public ModelAndView cancelar(Reserve reserve, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UserSystem userSystem) {

		validarReserve(reserve, result);
		if (result.hasErrors()) {
			return news(reserve);
		}
		
		try{
		cadasterReserveServive.cancelar(reserve);
		}catch (AccessDeniedException e){
			return new ModelAndView("/403");
		}

		attributes.addFlashAttribute("message", "Reserva cancelada com Sucesso!");
		return new ModelAndView("redirect:/reservations/"+reserve.getId());
	}
	
	private void validarReserve(Reserve reserve, BindingResult result) {
		reserve.addItens(tablesItensSession.getItens(reserve.getUuid()));
		reserve.calcularValorTotal();

		reserveValidator.validate(reserve, result);
	}

	private void setUuid(Reserve reserve) {
		if (StringUtils.isEmpty(reserve.getUuid())) {
			reserve.setUuid(UUID.randomUUID().toString());
		}
	}

}
