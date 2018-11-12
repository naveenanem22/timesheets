package com.tmsht.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsht.model.Timesheet;
import com.tmsht.service.TimesheetService;

@RestController(value = "timesheetController")
@RequestMapping("/employees/{id}/timesheets")
public class TimesheetController {

	@Autowired
	@Qualifier("timesheetServiceImpl")
	private TimesheetService timesheetService;

	@GetMapping
	public ResponseEntity<List<Timesheet>> getTimesheets(@PathVariable("id") int employeeId) {

		return new ResponseEntity<List<Timesheet>>(timesheetService.getTimesheetsByEmployeeId(employeeId),
				HttpStatus.OK);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTimesheets(@PathVariable("id") int employeeId,
			@Valid @RequestBody List<Timesheet> timesheets) {
		timesheetService.updateTimesheetsByEmployeeId(timesheets, employeeId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addTimesheets(@PathVariable("id") int employeeId,
			@Valid @RequestBody List<Timesheet> timesheets) {
		timesheetService.addTimesheetsByEmployeeId(timesheets, employeeId);
		return ResponseEntity.noContent().build();

	}

}
