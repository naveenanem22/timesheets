package com.tmsht.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmsht.dao.TimesheetDao;
import com.tmsht.model.Timesheet;

@Service(value = "timesheetServiceImpl")
public class TimesheetServiceImpl implements TimesheetService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

		LOGGER.debug("Received the employeeId: " + employeeId);
		LOGGER.debug("Received the timesheets data: " + timesheets.toString());
		// Create timesheet records for the employee in the timesheet table
		return timesheetDao.addTimesheetsByEmployeeId(timesheets, employeeId);

		// TODO: Get the timesheetIds of the rows created in the above step

		// TODO: Create nextleveltimesheetapproval records in the timesheetapproval
		// table

		// TODO: trigger an email to the approver
	}

	@Override
	@Transactional
	public boolean updateTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId) {
		return timesheetDao.updateTimesheetsByEmployeeId(timesheets, employeeId);
		// TODO: Get the timesheetIds of the rows created in the above step

		// TODO: Create nextleveltimesheetapproval records in the timesheetapproval
		// table

	}

	@Override
	public boolean approveTimeSheetsByTimesheetId(List<Integer> timesheetIds) {
		// TODO : Approve the timesheets in the timesheetapproval table with status as
		// APPROVED

		// TODO : Create nextlevel timesheetapproval record in the timesheeapproval
		// table

		// TODO: trigger an email to the approver
		return false;
	}

	@Override
	public boolean rejectTimeSheetsByTimesheetId(List<Integer> timesheetIds) {
		// TODO : Reject the timesheets by updating status to REJECTED in
		// timesheetapproval table

		// TODO: Trigger an email to the employee
		return false;
	}

	@Override
	public boolean createNextLevelTimesheetApprovalRecord(List<Integer> timesheetIds, int employeeId) {
		// TODO : Fetch next level manager or approver for the employee from LDAP server

		// TODO : Create nextlevel timesheetapproval record in the timesheetapproval
		// table with the manager fetched above

		return false;
	}

	@Override
	public boolean generateTimesheetApprovalRecordsByEmployeeId(List<Integer> timesheetIds, int employeeId) {
		// TODO: Fetch managerEmployeeIds for the employee by using employeeId from
		// either LDAP or Separate Table
		List<Integer> managerEmployeeIds = Arrays.asList(112234, 112234, 112234);

		// Create approval records in timesheetapproval table
		return timesheetDao.createTimesheetApprovalRecordsByTimesheetId(timesheetIds, managerEmployeeIds);
	}

}
