package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cg.entity.AppliedJobs;
import com.cg.entity.FavoriteJobs;
import com.cg.entity.Job;
import com.cg.entity.JobSeeker;
import com.cg.exception.JobSeekerException;
import com.cg.service.AddToApplyDO;
import com.cg.service.AppliedJobsDO;
import com.cg.service.FavoriteJobsDO;
import com.cg.service.JobSeekerServices;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @author admin
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/jss/jobSeekers")
public class JobSeekerController {

	@Autowired(required=false)
	@Qualifier(value="jobSeekerServiceSpring")
	private JobSeekerServices jobSeekerService;

	/***
	 * Add job Seeker to database
	 * @param jobSeeker
	 * @return String
	 *http://localhost:8082/api/jss/jobSeekers/
	 */	
	@PostMapping("/")
	public String registerJobSeeker(@RequestBody JobSeeker jobSeeker) throws JobSeekerException {
		try {
			JobSeeker status= jobSeekerService.registerJobSeeker(jobSeeker);
			if(!status.equals(null)) {
				System.out.println("employer:"+jobSeeker.getJobSeeker_Name()+" added to database");
				log.info("jobSeeker:"+jobSeeker.getJobSeeker_Name()+" added to database");
				return "jobSeeker:"+jobSeeker.getJobSeeker_Name()+" added to database";
			}else {
				log.debug("Unable to register jobSeeker");
				return "Unable to register jobSeeker to database";
			}
		}catch(JobSeekerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Search job by Location
	 * @param l1
	 * @return ResponseEntity
	 * http://localhost:8082/api/jss/jobSeekers/Pune
	 */
	@GetMapping("/{l1}")
	public ResponseEntity<List<Job>> getJobByLocation(@PathVariable("l1") String l1) throws JobSeekerException{
		try {
			List<Job> productList= jobSeekerService.getJobsByLocation(l1);
			return new ResponseEntity<>(productList,HttpStatus.OK);
		}catch(JobSeekerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Find Job By Id
	 * @param jobId
	 * @return ResponseEntity
	 * http://localhost:8082/api/jss/jobSeekers/job/21
	 */
	@GetMapping("/job/{jobId}")
	public ResponseEntity<Job> getJobById(@PathVariable Integer jobId) throws JobSeekerException{
		try {
			Job job= jobSeekerService.getJobById(jobId);
			return new ResponseEntity<>(job,HttpStatus.OK);
		}catch(JobSeekerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Apply for a job
	 * @param appliedJobsDO
	 * @return String
	 * http://localhost:8082/api/jss/jobSeekers/applyAJob/
	 */
	@PostMapping("/applyAJob/")
	public String applyAJob(@RequestBody AppliedJobsDO appliedJobsDO ) throws JobSeekerException {
		try {
			AppliedJobs status= jobSeekerService.applyAJob(appliedJobsDO);
			if(!status.equals(null)) {
				log.info("job:"+status.getAppliedJob_id()+" added to database");
				return "appliedJob:"+status.getAppliedJob_id()+" added to database";
			}else {
				log.debug("Unable to post a job");
				return "Unable to apply a job in the database";
			}
		}catch(JobSeekerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Add to favorite jobs
	 * @param favoriteJobsDO
	 * @return String
	 * http://localhost:8082/api/jss/jobSeekers/addToFav/
	 */
	@PostMapping("/addToFav/")
	public String addToFavJob(@RequestBody FavoriteJobsDO favoriteJobsDO ) throws JobSeekerException {
		try {
			FavoriteJobs status= jobSeekerService.addToFavJob(favoriteJobsDO);
			if(!status.equals(null)) {
				log.info("job:"+status.getFavJob_id()+" added to database");
				return "FavoriteJob:"+status.getFavJob_id()+" added to database";
			}else {
				log.debug("Unable to post a job");
				return "Unable to add favorite job in the database";
			}
		}catch(JobSeekerException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Get Favorite Jobs by jobseeker Id
	 * @param jobSeekerId
	 * @return String
	 * http://localhost:8082/api/jss/jobSeekers/getAllFavJobs/20
	 */
	@GetMapping("/getAllFavJobs/{jobSeekerId}") 
	public ResponseEntity<List<FavoriteJobs>> getAllFavJob(@PathVariable Integer jobSeekerId) throws JobSeekerException{ 
		try { 
			List<FavoriteJobs> jobList = jobSeekerService.getAllFavJobs(jobSeekerId); 
			return new ResponseEntity<>(jobList,HttpStatus.OK); 
		}catch(JobSeekerException e) { 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage()); 
		} 
	}

	/***
	 * Get all applied jobs by that job seeker
	 * @param jobSeekerId
	 * @return ResponseEntity
	 * http://localhost:8082/api/jss/jobSeekers/getAppliedJobs/22
	 */
	@GetMapping("/getAppliedJobs/{appliedJobId}")
	public ResponseEntity<List<AppliedJobs>> getAllAppliedJobs(@PathVariable Integer jobSeekerId) throws JobSeekerException{
		try {
			List<AppliedJobs> productList = jobSeekerService.getAllAppliedJobs(jobSeekerId);
			return new ResponseEntity<>(productList,HttpStatus.OK);
		}catch(JobSeekerException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Delete applied job by id
	 * @param id
	 * @return String
	 * http://localhost:8081/api/jss/jobSeekers/deleteAppliedJob/id
	 */
	@DeleteMapping("/deleteAppliedJob/{id}")
	public String deleteAppliedJob(@PathVariable Integer id) throws JobSeekerException {
		try {
			Integer status= jobSeekerService.deleteAppliedJob(id);
			if(!status.equals(null)) {
				return "job: "+id+" deleted from database";
			}else {
				return "Unable to delete product from database";
			}
		}catch(JobSeekerException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Delete favorite job by id
	 * @param id
	 * @return
	 * http://localhost:8082/api/jss/jobSeekers/deleteFavoriteJob/id
	 */
	@DeleteMapping("/deleteFavoriteJob/{id}")
	public String deleteFavoriteJob(@PathVariable Integer id) throws JobSeekerException {
		try {
			Integer status= jobSeekerService.deleteFavoriteJob(id);
			if(!status.equals(null)) {
				return "job: "+id+" deleted from database";
			}else {
				return "Unable to delete product from database";
			}
		}catch(JobSeekerException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Add to applied jobs from favorite jobs
	 * @param addToApplyDO
	 * @return String
	 * http://localhost:8082/api/jss/jobSeekers/addToApply/
	 */
	@PostMapping("/addToApply/")
	public String addToAppliedJob(@RequestBody AddToApplyDO addToApplyDO ) throws JobSeekerException {
		try {
			AppliedJobs status= jobSeekerService.addToAppliedJobs(addToApplyDO);
			if(!status.equals(null)) {
				System.out.println("job:"+addToApplyDO.getFavJobId()+" added to Applied Job");
				return "appliedJob:"+status.getAppliedJob_id()+" added to database";
			}else {
				System.out.println("Unable to post a job");
				return "Unable to apply a job in the database";
			}
		}catch(JobSeekerException e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

}
