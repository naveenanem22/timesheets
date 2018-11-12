package com.tmsht.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tmsht.model.Project;
import com.tmsht.model.Task;
import com.tmsht.model.Timesheet;

@Repository(value = "timesheetDaoImpl")
public class TimesheetDaoImpl implements TimesheetDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Timesheet> getTimesheetsByEmployeeId(int employeeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tmesht_id, tmesht_date, tmesht_start_time, tmesht_end_time, tmesht_status, ");
		sql.append("tsk_name, prj_project_id FROM timesheet ");
		sql.append("INNER JOIN task ON tmesht_tsk_id = tsk_id ");
		sql.append("INNER JOIN project ON tsk_prj_id = prj_id ");
		sql.append("WHERE tmesht_emp_id = :tmesht_emp_id");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tmesht_emp_id", employeeId);
		return namedParameterJdbcTemplate.query(sql.toString(), paramMap, new TimesheetRowMapper());
	}

	@Override
	public boolean addTimesheets(List<Timesheet> timesheets) {
		return false;
	}

	@Override
	public boolean updateTimesheets(List<Timesheet> timesheets) {
		return false;
	}

	@Override
	public boolean deleteTimesheetsById(List<Integer> timesheetIds) {
		return false;
	}

	private static class TimesheetRowMapper implements RowMapper<Timesheet> {

		@Override
		public Timesheet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Timesheet timesheet = new Timesheet();

			timesheet.setDate(rs.getDate("tmesht_date").toLocalDate());
			timesheet.setEndTime(rs.getTime("tmesht_end_time").toLocalTime());
			timesheet.setId(rs.getInt("tmesht_id"));
			timesheet.setProjectId(rs.getString("prj_project_id"));
			timesheet.setStartTime(rs.getTime("tmesht_start_time").toLocalTime());
			timesheet.setStatus(rs.getString("tmesht_status"));
			timesheet.setTaskName(rs.getString("tsk_name"));

			return timesheet;
		}

	}

}
