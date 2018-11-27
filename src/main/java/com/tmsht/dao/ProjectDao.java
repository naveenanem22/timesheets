package com.tmsht.dao;

import java.util.List;

import com.tmsht.model.Project;

public interface ProjectDao {
	List<Project> listProjects();

	int createProject(Project project);

	boolean updateProject(Project project);

	boolean deleteProejctById(int projectId);

	Project getProjectById(int projectId);

}
