package ca.psdev.mssc.jms.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.psdev.mssc.jms.config.JmsConfig;
import ca.psdev.mssc.jms.model.HelloWorldMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
public class HelloSender {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private JmsTemplate template;

	@Scheduled(fixedRate = 20000)
	public void sendMessage() {
		HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello World!").build();
		template.convertAndSend(JmsConfig.MY_QUEUE, message);
	}

	@Scheduled(fixedRate = 20000)
	public void sendAndReceiveMessage() throws Exception {
		HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello World!").build();

		Message resp = template.sendAndReceive(JmsConfig.MY_DUPLEX_QUEUE, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message helloMessage;
				try {
					helloMessage = session.createTextMessage(mapper.writeValueAsString(message));
				} catch (JsonProcessingException e) {
					throw new MessageFormatException("Unable to convert message into string " + e.getMessage());
				}
				String messageType = HelloWorldMessage.class.getCanonicalName();
				log.info(">>>" + messageType);
				helloMessage.setStringProperty("_type", messageType);
				return helloMessage;
			}
		});

		log.info("Got response " + resp.getBody(String.class));
	}

}
