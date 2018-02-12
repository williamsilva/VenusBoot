package com.alvorecer.venus.repository.helper.reserve;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alvorecer.venus.model.Reserve;
import com.alvorecer.venus.repository.filter.ReserveFilter;

public interface ReservesQueries {
	
	public Page<Reserve> filter(ReserveFilter reserveFilter, Pageable pageable);
	
	public Reserve buscarComItens(Long id);
}
