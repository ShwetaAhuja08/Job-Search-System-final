package com.cg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cg.entity.Employer;
import com.cg.entity.Job;
import com.cg.entity.JobSeeker;
import com.cg.exception.EmployerException;
import com.cg.service.EmployerService;

import lombok.extern.slf4j.Slf4j;

/***
 * 
 * @author admin
 *
 */

@Slf4j
@RestController
@RequestMapping("/api/jss/employers")
public class EmployerController {

	@Autowired
	private EmployerService employerService;
	
	/***
	 * Add Employer to database
	 * @param employer
	 * @return String 
	 * http://localhost:8082/api/jss/employers/
	 */
	@PostMapping("/")
	public String registerEmployer(@RequestBody Employer employer) throws EmployerException {
		try {
			Employer status= employerService.registerEmployer(employer);
			if(!status.equals(null)) {
				log.info("employer:"+employer.getOrganizationName()+" added to database");
				return "employer:"+employer.getOrganizationName()+" added to database";
			}else {
				log.debug("Unable to register employer");
				return "Unable to register employer to database";
			}
		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/***
	 * Add job details to the database
	 * @param job
	 * @param id
	 * @return String
	 * http://localhost:8082/api/jss/employers/postAJob/{id}
	 */	
	@PostMapping("/postAJob/{id}")
	public String postAJob(@RequestBody Job job, @PathVariable Integer id) throws EmployerException {
		try {
			Job status= employerService.postAjob(job,id);
			if(!status.equals(null)) {
				log.info("job:"+job.getTitle()+" added to database");
				return "job:"+job.getTitle()+" added to database";
			}else {
				log.debug("Unable to post a job");
				return "Unable to post job in the database";
			}

		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/***
	 * Find Job by job id
	 * @param jobId
	 * @return ResponseEntity<Job>
	 * http://localhost:8082/api/jss/employers/{jobId}
	 */
	@GetMapping("/{jobId}")
	public ResponseEntity<Job> getJobById(@PathVariable Integer jobId){
		try {
			Job job= employerService.getJobById(jobId);
			return new ResponseEntity<>(job,HttpStatus.OK);
		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/***
	 * Delete job by job id from job table 
	 * @param id
	 * @return String 
	 * http://localhost:8082/api/jss/employers/delete/id
	 */
	@DeleteMapping("/delete/{id}")
	public String deleteJob(@PathVariable Integer id) throws EmployerException {
		try {
			Integer status= employerService.deleteJob(id);
			if(!status.equals(null)) {
				log.info("Job: "+id+" deleted from database");
				return "job: "+id+" deleted from database";
			}else {
				log.debug("Unable to delete job from database");
				return "Unable to delete job from database";
			}

		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/***
	 * Get all jobs posted by employer id
	 * @param id
	 * @return ResponseEntity
	 * http://localhost:8082/api/jss/employers/getAllJobs/2
	 */
	@GetMapping("/getAllJobs/{id}")
	public ResponseEntity<List<Job>> getAllJob(@PathVariable Integer id) throws EmployerException{
		try {
			List<Job> jobList = employerService.getAllJobs(id);
			return new ResponseEntity<>(jobList,HttpStatus.OK);
		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/***
	 * Find JobSeeker by skills
	 * @param skill
	 * @return ResponseEntity
	 * http://localhost:8082/api/jss/employers/skill/Java
	 */
	@GetMapping("/skill/{skill}")
	public ResponseEntity<List<JobSeeker>> getJobSeekersBySkill(@PathVariable("skill") String skill) throws EmployerException{
		try {
			List<JobSeeker> jobSeekerList= employerService.searchJobSeekersBySkillSets(skill);
			return new ResponseEntity<>(jobSeekerList,HttpStatus.OK);
		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Update Job 
	 * @param job
	 * @return ResponseEntity
	 * http://localhost:8082/api/jss/employers/editAJob
	 */
	@PutMapping("/editAJob")
	public ResponseEntity<Job> updateJob(@RequestBody Job job) {
		try {
			Job updatedJob= employerService.editAJob(job);
			log.info("Job is updated");
			return new ResponseEntity<>(updatedJob,HttpStatus.OK);

		}catch(EmployerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
}