package com.tmsht.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsht.model.Project;
import com.tmsht.service.ProjectService;

@RestController(value = "projectController")
@RequestMapping("/v0/project-management/projects")
public class ProjectController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("projectServiceImpl")
	private ProjectService projectService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Project>> listAllProjects() {

		return new ResponseEntity<List<Project>>(projectService.listProjects(), HttpStatus.OK);

	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createProject(@Valid @RequestBody Project project) {
		projectService.createProject(project);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateProject(@Valid @RequestBody Project project,
			@PathVariable("id") int projectId) {
		project.setId(projectId);
		projectService.updateProject(project);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteProject(@Valid @PathVariable("id") int projectId) {
		projectService.deleteProejctById(projectId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> getProjectById(@Valid @PathVariable("id") int projectId) {
		return new ResponseEntity<Project>(projectService.getProjectById(projectId), HttpStatus.OK);

	}

}
