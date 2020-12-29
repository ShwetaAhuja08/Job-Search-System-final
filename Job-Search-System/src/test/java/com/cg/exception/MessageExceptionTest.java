package com.cg.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cg.entity.Message;

public class MessageExceptionTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	  void testExpectedException() {
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	Message fromDb = new Message("Applied Successfully");
			entityManager.persistAndFlush(fromDb);
	    });
	  }
}