package com.tmsht.dao;

import java.util.List;

import com.tmsht.model.Timesheet;

public interface TimesheetDao {
	List<Timesheet> getTimesheetsByEmployeeId(int employeeId);

	boolean addTimesheets(List<Timesheet> timesheets);

	boolean updateTimesheets(List<Timesheet> timesheets);

	boolean deleteTimesheetsById(List<Integer> timesheetIds);

}
