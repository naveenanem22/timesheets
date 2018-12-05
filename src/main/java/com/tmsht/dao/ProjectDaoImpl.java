package com.tmsht.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.pc.custom.exceptions.InternalServerException;
import com.pc.custom.exceptions.RecordNotFoundException;
import com.tmsht.model.Customer;
import com.tmsht.model.Project;

@Repository(value = "projectDaoImpl")
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Project> listProjects() {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM project");

			List<Project> projects = jdbcTemplate.query(sql.toString(), new ProjectRowMapper());
			LOGGER.debug("Fetched project records: {}" + projects.toString());
			return projects;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new InternalServerException(
					"Unexpected error occured while fetching list of all existing projects in the system.");
		}

	}

	@Override
	public int createProject(Project project) {
		StringBuilder sql = new StringBuilder();
		KeyHolder projectKey = new GeneratedKeyHolder();
		String[] keyColumnNames = new String[1];
		keyColumnNames[0] = "prj_id";
		int numberOfRowsAffected = 0;

		LOGGER.debug("Updating project object with Audit field values");
		project.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
		project.setUpdatedDate(LocalDateTime.now(ZoneOffset.UTC));

		sql.append("INSERT INTO project ");
		sql.append("(");
		sql.append("prj_name, prj_project_id, prj_service_type, prj_description, prj_planned_start_date, ");
		sql.append("prj_planned_end_date, prj_actual_start_date, prj_actual_end_date, ");
		sql.append("prj_created_date, prj_updated_date, prj_status, ");
		sql.append("prj_cust_id, prj_notes");
		sql.append(")");
		sql.append("VALUES ");
		sql.append("(");
		sql.append(":prj_name, :prj_project_id, :prj_service_type, :prj_description, :prj_planned_start_date, ");
		sql.append(":prj_planned_end_date, :prj_actual_start_date, :prj_actual_end_date, ");
		sql.append(":prj_created_date, :prj_updated_date, :prj_status, ");
		sql.append(":prj_cust_id, :prj_notes");
		sql.append(")");
		try {
			SqlParameterSource paramSource = new MapSqlParameterSource().addValue("prj_name", project.getName())
					.addValue("prj_project_id", project.getProjectId())
					.addValue("prj_service_type", project.getServiceType())
					.addValue("prj_description", project.getDescription())
					.addValue("prj_planned_start_date", project.getPlannedStartDate())
					.addValue("prj_planned_end_date", project.getPlannedEndDate())
					.addValue("prj_actual_start_date", project.getActualStartDate())
					.addValue("prj_actual_end_date", project.getActualEndDate())
					.addValue("prj_created_date", project.getCreatedDate())
					.addValue("prj_updated_date", project.getUpdatedDate()).addValue("prj_status", project.getStatus())
					.addValue("prj_cust_id", project.getCustomer().getId()).addValue("prj_notes", project.getNotes());
			numberOfRowsAffected = namedParameterJdbcTemplate.update(sql.toString(), paramSource, projectKey);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new InternalServerException(
					"Unexpected error occurred while creating a project: {}" + project.toString());
		}

		if (numberOfRowsAffected == 1) {
			LOGGER.debug("Project created successfully.");
			return projectKey.getKey().intValue();
		} else {
			LOGGER.debug("Unexpected error while creating project.");
			throw new InternalServerException(
					"Unexpected error occurred while creating project: {}" + project.toString());
		}
	}

	@Override
	public boolean updateProject(Project project) {
		int numberOfRowsAffected = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE project SET ");
		sql.append("prj_name =:prj_name, ");
		sql.append("prj_project_id =:prj_project_id, ");
		sql.append("prj_service_type =:prj_service_type, ");
		sql.append("prj_description =:prj_description, ");
		sql.append("prj_planned_start_date =:prj_planned_start_date, ");
		sql.append("prj_planned_end_date =:prj_planned_end_date, ");
		sql.append("prj_actual_start_date =:prj_actual_start_date, ");
		sql.append("prj_actual_end_date =:prj_actual_end_date, ");
		sql.append("prj_status =:prj_status, ");
		sql.append("prj_cust_id =:prj_cust_id, ");
		sql.append("prj_notes =:prj_notes ");
		sql.append("WHERE prj_id =:prj_id");

		try {
			SqlParameterSource paramSource = new MapSqlParameterSource().addValue("prj_name", project.getName())
					.addValue("prj_project_id", project.getProjectId())
					.addValue("prj_service_type", project.getServiceType())
					.addValue("prj_description", project.getDescription())
					.addValue("prj_planned_start_date", project.getPlannedStartDate())
					.addValue("prj_planned_end_date", project.getPlannedEndDate())
					.addValue("prj_actual_start_date", project.getActualStartDate())
					.addValue("prj_actual_end_date", project.getActualEndDate())
					.addValue("prj_status", project.getStatus()).addValue("prj_cust_id", project.getCustomer().getId())
					.addValue("prj_notes", project.getNotes()).addValue("prj_id", project.getId());
			numberOfRowsAffected = namedParameterJdbcTemplate.update(sql.toString(), paramSource);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected error occurred while updating project");
		}
		if (numberOfRowsAffected == 1) {
			LOGGER.debug("Updating project success");
			return true;
		} else {
			LOGGER.debug("Updating project failed");
			throw new InternalServerException("Unexpected error occurred while updating project");
		}
	}

	@Override
	public boolean deleteProejctById(int projectId) {
		int numberOfRowsAffected = 0;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("DELETE FROM project ");
			sql.append("WHERE prj_id =:prj_id");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("prj_id", projectId);
			numberOfRowsAffected = namedParameterJdbcTemplate.update(sql.toString(), paramMap);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected error occurred while updating project");
		}
		if (numberOfRowsAffected == 1) {
			LOGGER.debug("Project with id {} deleted successfully.", projectId);
			return true;
		}

		else {
			LOGGER.debug("Unexpected error while deleting the project with id: {}.", projectId);
			LOGGER.debug("Number of rows affected: {} is not equal to 1", numberOfRowsAffected);
			throw new InternalServerException("Unexpected error occurred while updating project");
		}
	}

	@Override
	public Project getProjectById(int projectId) {
		StringBuilder sql = new StringBuilder();
		List<Project> projects;
		try {
			sql.append("SELECT * FROM project ");
			sql.append("WHERE prj_id = :prj_id");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("prj_id", projectId);
			projects = namedParameterJdbcTemplate.query(sql.toString(), paramMap, new ProjectRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new InternalServerException("Unexpected error occurred while fetching the project");
		}
		if (projects.size() == 1) {
			LOGGER.debug("Fetched Project details: {}", projects.get(0).toString());
			return projects.get(0);
		} else {
			LOGGER.debug("Error while fetching the project with id: {}", projectId);
			LOGGER.debug("Total count of fetched projects: {} with projectID: {} is not equal to 1", projects.size(),
					projectId);
			throw new RecordNotFoundException("Project with id: " + projectId + " not found");
		}
	}

	private static class ProjectRowMapper implements RowMapper<Project> {

		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

			Project project = new Project();
			Customer customer = new Customer();
			customer.setId(rs.getInt("prj_cust_id"));
			project.setActualEndDate(rs.getDate("prj_actual_end_date").toLocalDate());
			project.setActualStartDate(rs.getDate("prj_actual_start_date").toLocalDate());
			project.setCreatedDate(rs.getTimestamp("prj_created_date").toLocalDateTime());
			project.setCustomer(customer);
			project.setDescription(rs.getString("prj_description"));
			project.setId(rs.getInt("prj_id"));
			project.setName(rs.getString("prj_name"));
			project.setNotes(rs.getString("prj_notes"));
			project.setPlannedEndDate(rs.getDate("prj_planned_end_date").toLocalDate());
			project.setPlannedStartDate(rs.getDate("prj_planned_start_date").toLocalDate());
			project.setProjectId(rs.getString("prj_project_id"));
			project.setServiceType(rs.getString("prj_service_type"));
			project.setStatus(rs.getString("prj_status"));
			project.setUpdatedDate(rs.getTimestamp("prj_updated_date").toLocalDateTime());

			return project;
		}

	}

}
