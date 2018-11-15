package com.tmsht.service;

import java.util.List;

import com.tmsht.model.Timesheet;

public interface TimesheetService {
	List<Timesheet> getTimesheetsByEmployeeId(int employeeId);

	boolean addTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId);

	boolean updateTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId);

	boolean approveTimeSheetsByTimesheetId(List<Integer> timesheetIds);

	boolean rejectTimeSheetsByTimesheetId(List<Integer> timesheetIds);

	boolean createNextLevelTimesheetApprovalRecord(List<Integer> timesheetIds, int employeeId);
	
	boolean generateTimesheetApprovalRecordsByEmployeeId(List<Integer> timesheetIds, int employeeId);

}
