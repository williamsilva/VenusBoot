package com.alvorecer.venus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.model.Attendance;
import com.alvorecer.venus.model.enun.AsPark;
import com.alvorecer.venus.model.enun.Channel;
import com.alvorecer.venus.model.enun.Subject;
import com.alvorecer.venus.model.enun.TypeClient;
import com.alvorecer.venus.model.enun.YesNo;
import com.alvorecer.venus.service.CadasterAttendenceService;
import com.alvorecer.venus.service.excepition.RegistroObrigatorioExcepition;

@Controller
@RequestMapping("/attendances")
public class AttendanceController {

	@Autowired
	private CadasterAttendenceService attendenceService;

	@GetMapping
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("attendance/ServiceList");
		return modelAndView;
	}

	@RequestMapping("/new")
	public ModelAndView news(Attendance attendance) {
		ModelAndView modelAndView = new ModelAndView("attendance/ServiceRegister");

		modelAndView.addObject("asParks", AsPark.values());
		modelAndView.addObject("subjects", Subject.values());
		modelAndView.addObject("close", YesNo.values());
		modelAndView.addObject("type", TypeClient.values());
		modelAndView.addObject("channels", Channel.values());

		return modelAndView;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView cadaster(@Valid Attendance attendance, BindingResult result, Model model,
			RedirectAttributes attributes) {
		System.out.println("Cpf Cnpj "+attendance.getCpfOuCnpj());
		if (result.hasErrors()) {
			return news(attendance);
		}

		try {
			attendenceService.cadaster(attendance);
		} catch (RegistroObrigatorioExcepition e) {
			return news(attendance);
		}

		attributes.addFlashAttribute("message", "Registro Salvo com Sucesso!");
		return new ModelAndView("redirect:/attendances/new");
	}
}