package com.alvorecer.venus.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alvorecer.venus.model.Reserve;
import com.alvorecer.venus.model.enun.StatusVouchers;
import com.alvorecer.venus.repository.Reserves;

@Service
public class CadasterReserveService {

	@Autowired
	private Reserves reserves;

	@Transactional
	public Reserve salvar(Reserve reserve) {
		
		if(reserve.isNew()){
			reserve.setCreationDate(LocalDateTime.now());
		}else{
			Reserve reserveExistente = reserves.findOne(reserve.getId());
			reserve.setCreationDate(reserveExistente.getCreationDate());
		}
		return reserves.saveAndFlush(reserve);
	}
	
	@Transactional
	public void emitir(Reserve reserve) {
		reserve.setStatusVouchers(StatusVouchers.TROCADO);
		salvar(reserve);
		
	}
	
	@PreAuthorize("#reserve.use == principal.use or hasRole('CANCELAR_VOUCHER')")
	@Transactional
	public void cancelar(Reserve reserve) {
		reserve.setStatusVouchers(StatusVouchers.CANCELADO);
		salvar(reserve);		
	}
}
