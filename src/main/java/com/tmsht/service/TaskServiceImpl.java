package com.tmsht.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmsht.dao.TaskDao;
import com.tmsht.model.Task;

@Service(value = "taskServiceImpl")
public class TaskServiceImpl implements TaskService {

	@Autowired
	@Qualifier(value = "taskDaoImpl")
	private TaskDao taskDao;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional(readOnly = true)
	public List<Task> listAllTasks() {

		return taskDao.listAllTasks();
	}

	@Override
	@Transactional
	public boolean createTasks(List<Task> tasks) {

		return taskDao.createTasks(tasks);
	}

	@Override
	@Transactional
	public boolean updateTasks(List<Task> tasks) {

		return taskDao.updateTasks(tasks);
	}

	@Override
	@Transactional
	public boolean deleteTasksById(Set<Integer> taskIds) {
		return taskDao.deleteTasksById(taskIds);
	}

}
