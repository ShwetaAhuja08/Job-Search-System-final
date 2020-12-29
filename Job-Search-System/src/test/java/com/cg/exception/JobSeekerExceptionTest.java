package com.cg.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cg.entity.JobSeeker;

public class JobSeekerExceptionTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	  void testExpectedException() {
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	JobSeeker fromDb = new JobSeeker("neha");
			entityManager.persistAndFlush(fromDb);
	    });
	  }
}