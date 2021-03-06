package com.cg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.cg.JobSearchSystem.JobSearchSystemApplication;
import com.cg.dao.EmployerSpringDataDAO;
import com.cg.dao.JobSpringDataDAO;
import com.cg.entity.Employer;
import com.cg.entity.Job;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = JobSearchSystemApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EmployerControllerTest {


	@Autowired
	private MockMvc mvc;
	@Autowired
    private TestRestTemplate restTemplate;

	@Autowired
	private EmployerSpringDataDAO employerSpringDatadao;
	@Autowired
	private JobSpringDataDAO jobSpringDatadao;

	
	@Test
	public void whenValidInput_thenRegisterEmployer() throws IOException, Exception {
		Employer infosys = new Employer("Infosys");
		Employer tcs = new Employer("TCS");

		mvc.perform(post("/jss/employers").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(infosys)));
		mvc.perform(post("/jss/employers").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tcs)));

		List<Employer> found = employerSpringDatadao.findAll();
		assertThat(found).extracting(Employer::getOrganizationName).containsOnly("Infosys","TCS");


	}
	@Test
	public void whenValidInput_thenPostAJob() throws IOException, Exception {
		Job j1 = new Job("Business Analyst");
		Job j2 = new Job("Data Analyst");

		mvc.perform(post("/jss/employers/postAJob/59").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(j1)));
		mvc.perform(post("/jss/employers/postAJob/60").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(j2)));

		List<Job> found = jobSpringDatadao.findAll();
		assertThat(found).extracting(Job::getTitle).containsOnly("Business Analyst","Data Analyst");	

	}
	@Test
	public void givenPostAJob_whenGetJobById_thenStatus200() throws Exception {
		String id = "72";
		mvc.perform(get("/jss/employers/{id}",id).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").exists());

	}
	
	@Test
    public void testDeleteJob() {
         int id = 1;
         Job job = restTemplate.getForObject("/jss/employers/delete/"+id, Job.class);
         restTemplate.delete("/jss/employers/delete/"+id);
         try {
              job = restTemplate.getForObject("/jss/employers/delete/"+id, Job.class);
         } catch (final HttpClientErrorException e) {
              assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
    }
	
	

}