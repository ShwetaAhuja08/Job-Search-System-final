package com.cg.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cg.dao.EmployerSpringDataDAO;
import com.cg.entity.Employer;

public class EmployerExceptionTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	  void testExpectedException() {
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	Employer fromDb = new Employer("capgemini");
			entityManager.persistAndFlush(fromDb);
	    });
	  }
}