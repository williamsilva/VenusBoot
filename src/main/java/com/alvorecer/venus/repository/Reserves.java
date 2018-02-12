package com.alvorecer.venus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alvorecer.venus.model.Reserve;
import com.alvorecer.venus.repository.helper.reserve.ReservesQueries;

@Repository
public interface Reserves extends JpaRepository<Reserve, Long>, ReservesQueries {

}
