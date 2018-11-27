package com.tmsht.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmsht.dao.ProjectDao;
import com.tmsht.model.Project;

@Service(value = "projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	@Qualifier("projectDaoImpl")
	private ProjectDao projectDao;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional(readOnly = true)
	public List<Project> listProjects() {
		return projectDao.listProjects();
	}

	@Override
	@Transactional
	public int createProject(Project project) {
		return projectDao.createProject(project);
	}

	@Override
	@Transactional
	public boolean updateProject(Project project) {
		return projectDao.updateProject(project);
	}

	@Override
	@Transactional
	public boolean deleteProejctById(int projectId) {
		return projectDao.deleteProejctById(projectId);
	}

	@Override
	@Transactional(readOnly = true)
	public Project getProjectById(int projectId) {
		return projectDao.getProjectById(projectId);
	}

}
