package com.tmsht.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pmt.custom.exceptions.InternalServerException;
import com.pmt.model.Employee;
import com.tmsht.model.Project;
import com.tmsht.model.ResourceAllocation;
import com.tmsht.model.Task;

@Repository(value = "resourceAllocationDaoImpl")
public class ResourceAllocationDaoImpl implements ResourceAllocationDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean createResourceAllocationByTask(List<Employee> employees, Task task, String notes) {
		LOGGER.debug("Inserting Resource-Allocation records for the emloyeeIds: {} under the taskId: {}",
				employees.toString(), task.getId());
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO resourceallocation ");
			sql.append("(");
			sql.append("ra_emp_id, ra_notes, ra_tsk_id");
			sql.append(")");
			sql.append("VALUES ");
			sql.append("(");
			sql.append(":ra_emp_id, :ra_notes, :ra_tsk_id");
			sql.append(")");

			List<Map<String, Object>> batchValues = new ArrayList<>(employees.size());
			employees.forEach(employee -> {
				batchValues.add(new MapSqlParameterSource("ra_emp_id", employee.getId())
						.addValue("ra_tsk_id", task.getId()).addValue("ra_notes", notes).getValues());

			});

			namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[employees.size()]));
			LOGGER.debug("Insert SUCCESS.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Exception occured while allocating resources to task.");
			throw new InternalServerException("Unexpected error while allocating resources to task");

		}
	}

	@Override
	public boolean createResourceAllocationByResource(List<Task> tasks, Employee employee, String notes) {
		LOGGER.debug("Inserting Resource-Allocation records for the taskIds: {} for the employeeId: {}",
				tasks.toString(), employee.toString());
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO resourceallocation ");
			sql.append("(");
			sql.append("ra_emp_id, ra_notes, ra_tsk_id");
			sql.append(")");
			sql.append("VALUES ");
			sql.append("(");
			sql.append(":ra_emp_id, :ra_notes, :ra_tsk_id");
			sql.append(")");

			List<Map<String, Object>> batchValues = new ArrayList<>(tasks.size());
			tasks.forEach(task -> {
				batchValues.add(new MapSqlParameterSource("ra_emp_id", employee.getId())
						.addValue("ra_tsk_id", task.getId()).addValue("ra_notes", notes).getValues());

			});

			namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[tasks.size()]));
			LOGGER.debug("Insert SUCCESS.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Exception occured while allocating resources to task.");
			throw new InternalServerException("Unexpected error while allocating resources to task");

		}
	}

	@Override
	public boolean removeResourceAllocationById(Set<Integer> resourceAllocationIds) {
		Integer numberOfRowsAffected;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM resourceallocation ");
			sql.append("WHERE ra_id IN (:ra_ids)");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ra_ids", resourceAllocationIds);
			numberOfRowsAffected = namedParameterJdbcTemplate.update(sql.toString(), paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Exception occured while deallocating resources from task.");
			throw new InternalServerException("Unexpected error while allocating resources to task");

		}
		if (numberOfRowsAffected == resourceAllocationIds.size())
			return true;
		else {
			LOGGER.debug(
					"No of resourceallocation records removed in the database {} is not matching with the count {} in the incoming request.",
					numberOfRowsAffected, resourceAllocationIds.size());
			throw new InternalServerException("Unexpected error while allocating resources to task");
		}

	}

	@Override
	public List<ResourceAllocation> fetchResourceAllocationIdsByResourceId(int employeeId) {
		LOGGER.debug("Fetching resource-allocationIds for the employeeId: {}", employeeId);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ra_id, ra_notes, tsk_id, tsk_name, emp_firstname, emp_lastname, prj_id, prj_project_id ");
		sql.append("FROM resourceallocation ");
		sql.append("INNER JOIN task ON ra_tsk_id = tsk_id ");
		sql.append("INNER JOIN project ON tsk_prj_id = prj_id ");
		sql.append("INNER JOIN employee ON ra_emp_id = emp_id ");
		sql.append("WHERE ra_emp_id = :ra_emp_id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ra_emp_id", employeeId);
		List<ResourceAllocation> resourceAllocations = namedParameterJdbcTemplate.query(sql.toString(), paramMap, new ResourceAllocationRowMapper());
		LOGGER.debug("Fetch SUCCESS");
		LOGGER.debug("Fetched resourceAllocations {} for the employeeId {}", resourceAllocations.toString(),
				employeeId);
		return resourceAllocations;
	}

	@Override
	public Set<Integer> fetchResourceAllocationIdsByTaskId(int taskId) {
		return null;
	}

	private static class ResourceAllocationIdRowMapper implements RowMapper<Integer> {

		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Integer resourceAllocationId;
			resourceAllocationId = rs.getInt("ra_id");
			return resourceAllocationId;
		}

	}

	private static class ResourceAllocationRowMapper implements RowMapper<ResourceAllocation> {

		@Override
		public ResourceAllocation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setId(rs.getInt("prj_id"));
			project.setProjectId(rs.getString("prj_project_id"));

			Task task = new Task();
			task.setId(rs.getInt("tsk_id"));
			task.setName(rs.getString("tsk_name"));
			task.setProject(project);
			
			Employee resource = new Employee();
			resource.setFirstName(rs.getString("emp_firstname"));
			resource.setLastName(rs.getString("emp_lastname"));
			

			ResourceAllocation resourceAllocation = new ResourceAllocation();
			resourceAllocation.setTask(task);
			resourceAllocation.setId(rs.getInt("ra_id"));
			resourceAllocation.setNotes(rs.getString("ra_notes"));
			resourceAllocation.setResource(resource);

			return resourceAllocation;
		}

	}

}
