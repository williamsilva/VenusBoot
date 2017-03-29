package com.alvorecer.venus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Attendance;
import com.alvorecer.venus.repository.Attendances;
import com.alvorecer.venus.service.excepition.RegistroObrigatorioExcepition;

@Service
public class CadasterAttendenceService {

	@Autowired
	private Attendances attendances;

	@Transactional
	public void cadaster(Attendance attendance) {
		if (StringUtils.isEmpty(attendance.getClient())) {
			throw new RegistroObrigatorioExcepition("Informe um Cliente");
		}
		attendances.save(attendance);
	}
}