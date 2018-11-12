package com.tmsht.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
	public boolean addTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO timesheet ");
		sql.append("(");
		sql.append("tmesht_tsk_id, tmesht_emp_id, tmesht_date, tmesht_start_time, ");
		sql.append("tmesht_end_time, tmesht_status");
		sql.append(")");
		sql.append("VALUES ");
		sql.append("(");
		sql.append(":tmesht_tsk_id, :tmesht_emp_id, :tmesht_date, :tmesht_start_time, ");
		sql.append(":tmesht_end_time, :tmesht_status");
		sql.append(")");

		List<Map<String, Object>> batchValues = new ArrayList<>(timesheets.size());
		timesheets.forEach(timesheet -> {
			batchValues.add(new MapSqlParameterSource("tmesht_tsk_id", timesheet.getTask().getId())
					.addValue("tmesht_emp_id", employeeId).addValue("tmesht_date", timesheet.getDate())
					.addValue("tmesht_start_time", timesheet.getStartTime())
					.addValue("tmesht_end_time", timesheet.getEndTime())
					.addValue("tmesht_status", timesheet.getStatus()).getValues());
		});

		namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[timesheets.size()]));

		return true;

	}

	@Override
	public boolean updateTimesheetsByEmployeeId(List<Timesheet> timesheets, int employeeId) {
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE timesheet SET ");
		sql.append("tmesht_tsk_id =:tmesht_tsk_id, ");
		sql.append("tmesht_start_time =:tmesht_start_time, ");
		sql.append("tmesht_end_time =:tmesht_end_time ");
		sql.append("WHERE tmesht_id =:tmesht_id");

		List<Map<String, Object>> batchValues = new ArrayList<>(timesheets.size());
		timesheets.forEach(timesheet -> {
			batchValues.add(new MapSqlParameterSource("tmesht_tsk_id", timesheet.getTask().getId())
					.addValue("tmesht_emp_id", employeeId).addValue("tmesht_date", timesheet.getDate())
					.addValue("tmesht_start_time", timesheet.getStartTime())
					.addValue("tmesht_end_time", timesheet.getEndTime()).addValue("tmesht_id", timesheet.getId())
					.getValues());
		});

		namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[timesheets.size()]));
		return true;
	}

	@Override
	public boolean deleteTimesheetsById(List<Integer> timesheetIds) {
		return false;
	}

	private static class TimesheetRowMapper implements RowMapper<Timesheet> {

		@Override
		public Timesheet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Timesheet timesheet = new Timesheet();
			Project project = new Project();
			Task task = new Task();

			project.setProjectId(rs.getString("prj_project_id"));
			task.setName(rs.getString("tsk_name"));

			timesheet.setDate(rs.getDate("tmesht_date").toLocalDate());
			timesheet.setEndTime(rs.getTime("tmesht_end_time").toLocalTime());
			timesheet.setId(rs.getInt("tmesht_id"));
			timesheet.setStartTime(rs.getTime("tmesht_start_time").toLocalTime());
			timesheet.setStatus(rs.getString("tmesht_status"));
			timesheet.setTask(task);
			timesheet.setProject(project);

			return timesheet;
		}

	}

}
