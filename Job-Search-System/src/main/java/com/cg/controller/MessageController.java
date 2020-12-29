package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.cg.entity.Message;
import com.cg.exception.MessageException;
import com.cg.service.MessageDO;
import com.cg.service.MessageService;
import lombok.extern.slf4j.Slf4j;

/***
 * 
 * @author admin
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/jss/message")
public class MessageController {

	@Autowired(required=false)
	@Qualifier(value="messageService")
	private MessageService messageService;

	/***
	 * Send message to the employer 
	 * @param messageDO
	 * @return String
	 * http://localhost:8082/api/jss/message/sendMsgToEmployer/
	 */
	@PostMapping("/sendMsgToEmployer")
	public String sendMessageToEmployer(@RequestBody MessageDO messageDO ) throws MessageException {
		try {
			Message status= messageService.sendMessageToEmployer(messageDO);
			if(!status.equals(null)) {
				log.info("Message" + status.getDescription()+"added to database");
				return "Message:"+status.getDescription()+" added to database";
			}else {
				log.debug("Message" + status.getDescription()+"not added to database");
				return "Unable to apply a job in the database";
			}
		}catch(MessageException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	/***
	 * Send message to job seeker
	 * @param messageDO
	 * @return String
	 * http://localhost:8082/api/jss/message/sendMsgToJobSeeker/
	 */
	@PostMapping("/sendMsgToJobSeeker/")
	public String sendMessageToJobSeeker(@RequestBody MessageDO messageDO ) throws MessageException {
		try {
			Message status= messageService.sendMessageToJobseeker(messageDO);
			if(!status.equals(null)) {
				log.info("Mesage" + status.getDescription()+"added to database");
				return "Message:"+status.getDescription()+" added to database";
			}else {
				log.debug("Message" + status.getDescription()+"not added to database");
				return "Unable to apply a job in the database";
			}
		}catch(MessageException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

}
