package com.tmsht.service;

import java.util.List;

import com.tmsht.model.Project;

public interface ProjectService {
	List<Project> listProjects();

	int createProject(Project project);

	boolean updateProject(Project project);

	boolean deleteProejctById(int projectId);

	Project getProjectById(int projectId);

}
