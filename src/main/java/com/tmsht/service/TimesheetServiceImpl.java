package com.tmsht.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmsht.dao.TimesheetDao;
import com.tmsht.model.Timesheet;

@Service(value = "timesheetServiceImpl")
public class TimesheetServiceImpl implements TimesheetService {

	@Autowired
	@Qualifier(value = "timesheetDaoImpl")
	private TimesheetDao timesheetDao;

	@Override
	@Transactional(readOnly = true)
	public List<Timesheet> getTimesheetsByEmployeeId(int employeeId) {
		return timesheetDao.getTimesheetsByEmployeeId(employeeId);
	}

	@Override
	@Transactional
	public boolean addTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId) {
		return timesheetDao.addTimesheetsByEmployeeId(timesheets, employeeId);
	}

	@Override
	@Transactional
	public boolean updateTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId) {
		return timesheetDao.updateTimesheetsByEmployeeId(timesheets, employeeId);
	}

}
