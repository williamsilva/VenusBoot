package com.alvorecer.venus.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alvorecer.venus.dto.PeriodoRelatorio;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@GetMapping("/vouchersEmitidos")
	public ModelAndView reportVouchersEmitidos(){
		ModelAndView modelAndView = new ModelAndView("formSystem/report/ReportVouchersEmitido");
		modelAndView.addObject(new PeriodoRelatorio());
		
		return modelAndView;
	}
	
	@PostMapping("/vouchersEmitidos")
	public ModelAndView gerarReportVouchersEmitidos(PeriodoRelatorio periodoRelatorio){
		
		Map<String, Object> parametros = new HashMap<>();
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		parametros.put("format", "pdf");
		parametros.put("date_inicio", dataInicio);
		parametros.put("date_fim", dataFim);
		
		return new ModelAndView("report_vouchers_emitido", parametros);
	}
	
}
