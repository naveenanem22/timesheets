package com.tmsht.dao;

import java.util.List;

import com.tmsht.model.Timesheet;

public interface TimesheetDao {
	List<Timesheet> getTimesheetsByEmployeeId(int employeeId);

	boolean addTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId);

	boolean updateTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId);

	boolean deleteTimesheetsById(List<Integer> timesheetIds);

}
