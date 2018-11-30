package com.tmsht.dao;

import java.util.List;
import java.util.Set;

import com.tmsht.model.Task;

public interface TaskDao {

	List<Task> listAllTasks();

	boolean createTasks(List<Task> tasks);

	boolean updateTasks(List<Task> tasks);

	boolean deleteTasksById(Set<Integer> taskIds);


}
