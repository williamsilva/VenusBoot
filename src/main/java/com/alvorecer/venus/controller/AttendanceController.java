package com.alvorecer.venus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alvorecer.venus.Util;
import com.alvorecer.venus.controller.page.PageWrapper;
import com.alvorecer.venus.model.Attendance;
import com.alvorecer.venus.model.enun.AsPark;
import com.alvorecer.venus.model.enun.Channel;
import com.alvorecer.venus.model.enun.Subject;
import com.alvorecer.venus.model.enun.TypeClientEnun;
import com.alvorecer.venus.model.enun.YesNo;
import com.alvorecer.venus.repository.Attendances;
import com.alvorecer.venus.repository.filter.AttendanceFilter;
import com.alvorecer.venus.service.CadasterAttendenceService;
import com.alvorecer.venus.service.excepition.RegistroObrigatorioExcepition;

@Controller
@RequestMapping("/attendances")
public class AttendanceController {

	@Autowired
	private CadasterAttendenceService attendenceService;

	@Autowired
	private Attendances attendances;

	private String BaseURL = "formSystem/attendance/";

	@GetMapping
	public ModelAndView list(AttendanceFilter attendanceFilter, BindingResult result,
			@PageableDefault(size = 8) Pageable pageable, HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView(BaseURL + "ServiceList");
		modelAndView.addObject("asParks", AsPark.values());
		modelAndView.addObject("subjects", Subject.values());
		modelAndView.addObject("close", YesNo.values());
		modelAndView.addObject("type", TypeClientEnun.values());
		modelAndView.addObject("channels", Channel.values());

		PageWrapper<Attendance> pageWrapper = new PageWrapper<>(attendances.filter(attendanceFilter, pageable),
				httpServletRequest);

		modelAndView.addObject("page", pageWrapper);

		return modelAndView;
	}

	@RequestMapping("/new")
	public ModelAndView news(Attendance attendance) {
		ModelAndView modelAndView = new ModelAndView(BaseURL + "ServiceRegister");

		modelAndView.addObject("asParks", AsPark.values());
		modelAndView.addObject("subjects", Subject.values());
		modelAndView.addObject("close", YesNo.values());
		modelAndView.addObject("type", TypeClientEnun.values());
		modelAndView.addObject("channels", Channel.values());

		if (attendance.getProtocol() == null) {
			attendance.setProtocol(Util.protocol());
		}

		if (attendance.getHourRegister() == null) {
			attendance.setHourRegister(Util.hora());
		}

		return modelAndView;
	}

	@PostMapping({ "/new", "{\\+d}" })
	public ModelAndView cadaster(@Valid Attendance attendance, BindingResult result, Model model,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return news(attendance);
		}

		try {
			attendenceService.cadaster(attendance);
		} catch (RegistroObrigatorioExcepition e) {
			result.rejectValue("client", e.getMessage(), e.getMessage());
			return news(attendance);
		}

		attributes.addFlashAttribute("message", "Registro Salvo com Sucesso!");
		return new ModelAndView("redirect:/attendances/new");
	}

	@GetMapping("/{id}")
	public ModelAndView edit(@PathVariable("id") Attendance attendance) {
		ModelAndView modelAndView = news(attendance);
		modelAndView.addObject(attendance);

		return modelAndView;
	}
}