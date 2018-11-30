package com.tmsht.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pmt.custom.exceptions.InternalServerException;
import com.tmsht.model.Task;

@Repository(value = "taskDaoImpl")
public class TaskDaoImpl implements TaskDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Task> listAllTasks() {
		StringBuilder sql = new StringBuilder();
		List<Task> tasks;
		try {
			sql.append("SELECT * FROM task");
			tasks = namedParameterJdbcTemplate.query(sql.toString(), new TaskRowMapper());
			LOGGER.debug("Tasks fetched: " + tasks.toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected error while fetching tasks.");
		}

		return tasks;

	}

	@Override
	public boolean createTasks(List<Task> tasks) {
		int[] numberOfRowsAffectedArray = {};
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO task ");
			sql.append("(");
			sql.append("tsk_prj_id, tsk_name, tsk_description, tsk_start_date, tsk_end_date");
			sql.append(")");
			sql.append("VALUES ");
			sql.append("(");
			sql.append(":tsk_prj_id, :tsk_name, :tsk_description, :tsk_start_date, :tsk_end_date");
			sql.append(")");

			List<Map<String, Object>> batchValues = new ArrayList<>(tasks.size());
			tasks.forEach(task -> {
				batchValues.add(new MapSqlParameterSource("tsk_prj_id", task.getProject().getId())
						.addValue("tsk_name", task.getName()).addValue("tsk_description", task.getDescription())
						.addValue("tsk_start_date", task.getStartDate()).addValue("tsk_end_date", task.getEndDate())
						.getValues());

			});

			numberOfRowsAffectedArray = namedParameterJdbcTemplate.batchUpdate(sql.toString(),
					batchValues.toArray(new Map[tasks.size()]));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected exception occurred while creating tasks");

		}
		if (IntStream.of(numberOfRowsAffectedArray).sum() == tasks.size()) {
			LOGGER.debug("Creation of tasks successful.");
			return true;
		}

		else {
			LOGGER.debug("Creation of tasks failed.");
			LOGGER.debug(
					"Number of tasks to be created: {} is not matching with the number of task rows inserted in database: {}",
					tasks.size(), IntStream.of(numberOfRowsAffectedArray).sum());
			throw new InternalServerException("Unexpected exception occurred while creating tasks");

		}

	}

	private static class TaskRowMapper implements RowMapper<Task> {

		@Override
		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
			Task task = new Task();
			task.setDescription(rs.getString("tsk_description"));
			task.setEndDate(rs.getDate("tsk_end_date").toLocalDate());
			task.setId(rs.getInt("tsk_id"));
			task.setName(rs.getString("tsk_name"));
			task.setStartDate(rs.getDate("tsk_start_date").toLocalDate());
			return task;
		}

	}

	@Override
	public boolean updateTasks(List<Task> tasks) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE task SET ");
			sql.append("tsk_prj_id =:tsk_prj_id, ");
			sql.append("tsk_name =:tsk_name, ");
			sql.append("tsk_description =:tsk_description, ");
			sql.append("tsk_start_date =:tsk_start_date, ");
			sql.append("tsk_end_date =:tsk_end_date ");
			sql.append("WHERE tsk_id =:tsk_id");

			List<Map<String, Object>> batchValues = new ArrayList<>(tasks.size());
			tasks.forEach(task -> {
				batchValues.add(new MapSqlParameterSource("tsk_prj_id", task.getProject().getId())
						.addValue("tsk_name", task.getName()).addValue("tsk_description", task.getDescription())
						.addValue("tsk_start_date", task.getStartDate()).addValue("tsk_end_date", task.getEndDate())
						.addValue("tsk_id", task.getId()).getValues());

			});

			namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[tasks.size()]));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected exception occurred while updating tasks");
		}
		return true;
	}

	@Override
	public boolean deleteTasksById(Set<Integer> taskIds) {
		StringBuilder sql = new StringBuilder();
		int numberOfRowsAffected = 0;
		sql.append("DELETE FROM task ");
		sql.append("WHERE tsk_id IN (:taskIds)");

		Map<String, Object> paramMap = new HashMap<String, Object>() {
			{
				put("taskIds", taskIds);
			}
		};

		try {
			numberOfRowsAffected = namedParameterJdbcTemplate.update(sql.toString(), paramMap);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected exception occurred while deleting tasks");

		}

		if (numberOfRowsAffected == taskIds.size()) {
			LOGGER.debug("Deletion of tasks with ids:{} successful.", taskIds.toString());
			return true;
		}

		else {
			LOGGER.debug("Deletion of tasks failed.");
			LOGGER.debug(
					"The number of tasks to be deleted: {} is not equal to number of task-rows deleted from database: {}",
					taskIds.size(), numberOfRowsAffected);
			throw new InternalServerException("Unexpected exception occurred while deleting tasks");
		}

	}

}
