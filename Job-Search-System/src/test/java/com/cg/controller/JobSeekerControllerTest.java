package com.cg.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cg.JobSearchSystem.JobSearchSystemApplication;
import com.cg.dao.AppliedJobsDAO;
import com.cg.dao.FavoriteJobsDAO;
import com.cg.dao.JobSeekerSpringDataDao;
import com.cg.entity.AppliedJobs;
import com.cg.entity.FavoriteJobs;
import com.cg.entity.Job;
import com.cg.entity.JobSeeker;
import com.cg.service.AppliedJobsDO;
import com.cg.service.FavoriteJobsDO;
import com.cg.service.JobSeekerServices;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = JobSearchSystemApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase(replace=Replace.NONE)

public class JobSeekerControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private JobSeekerServices jobSeekerService;
	@Autowired
	private JobSeekerSpringDataDao jobSeekerSpringDatadao;
	@Autowired
	private AppliedJobsDAO appliedJobsDAO;
	@Autowired
	private FavoriteJobsDAO favoriteJobsDAO;
	@Autowired
	private TestRestTemplate restTemplate;

	    @Test
	    public void whenValidInput_thenRegisterJobSeeker() throws IOException, Exception {
	    	JobSeeker neelam = new JobSeeker("Neelam");
	    	JobSeeker priyanka = new JobSeeker("abc");
	    	
	    	mvc.perform(post("/jss/jobSeekers/").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(neelam)));
	    	mvc.perform(post("/jss/jobSeekers/").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(priyanka)));
	    	
	    	List<JobSeeker> found = jobSeekerSpringDatadao.findAll();
	        assertThat(found).extracting(JobSeeker::getJobSeeker_Name).containsOnly("Neelam","abc");
		    	
	    }

		@Test
		public void whenValidInput_thenApplyAJob() throws IOException, Exception{
			AppliedJobsDO appliedJobs = new AppliedJobsDO(1);
			AppliedJobsDO appliedJobs1 = new AppliedJobsDO(71);
			mvc.perform(post("/jss/jobSeekers/applyAJob").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(appliedJobs)));
			mvc.perform(post("/jss/jobSeekers/applyAJob").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(appliedJobs1)));
			List<AppliedJobs> found = appliedJobsDAO.findAll();
			assertThat(found).size().isEqualTo(2);
	
		}
	
		@Test
		public void whenValidInput_thenAddToFavJob() throws IOException, Exception{
			FavoriteJobsDO favJobs = new FavoriteJobsDO(71);
			FavoriteJobsDO favJobs1 = new FavoriteJobsDO(71);
			mvc.perform(post("/jss/jobSeekers/addToFav").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(favJobs)));
			mvc.perform(post("/jss/jobSeekers/addToFav").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(favJobs1)));
			List<FavoriteJobs> found = favoriteJobsDAO.findAll();
			assertThat(found).size().isEqualTo(2);
	}
	   
		@Test
		public void testDeleteAppliedJob() {
			int id = 1;
			AppliedJobs appliedJobs = restTemplate.getForObject("/jss/jobSeekers/deleteAppliedJob/"+id, AppliedJobs.class);
			restTemplate.delete("/jss/jobSeekers/deleteAppliedJobs/"+id);
			try {
				appliedJobs = restTemplate.getForObject("/jss/jobSeekers/deleteAppliedJob/"+id, AppliedJobs.class);
			} catch (final HttpClientErrorException e) {
				assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
		@Test
		public void testDeleteFavoriteJob() {
			int id = 78;
			FavoriteJobs favoriteJobs = restTemplate.getForObject("/jss/jobSeekers/deleteFavoriteJob/"+id,FavoriteJobs.class);
			restTemplate.delete("/jss/jobSeekers/deleteAppliedJobs/"+id);
			try {
				favoriteJobs = restTemplate.getForObject("/jss/jobSeekers/deleteFavoriteJob/"+id, FavoriteJobs.class);
			} catch (final HttpClientErrorException e) {
				assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}	
		
		
}
