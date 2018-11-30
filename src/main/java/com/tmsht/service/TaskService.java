package com.tmsht.service;

import java.util.List;
import java.util.Set;

import com.tmsht.model.Task;

public interface TaskService {

	List<Task> listAllTasks();

	boolean createTasks(List<Task> tasks);

	boolean updateTasks(List<Task> tasks);

	boolean deleteTasksById(Set<Integer> taskIds);

}
