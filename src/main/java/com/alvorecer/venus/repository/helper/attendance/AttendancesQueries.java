package com.alvorecer.venus.repository.helper.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alvorecer.venus.model.Attendance;
import com.alvorecer.venus.repository.filter.AttendanceFilter;

public interface AttendancesQueries {
	
	public Page<Attendance> filter(AttendanceFilter attendanceFilter, Pageable pageable);
}
