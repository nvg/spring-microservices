package ca.psdev.mssc.jms.listener;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import ca.psdev.mssc.jms.config.JmsConfig;
import ca.psdev.mssc.jms.model.HelloWorldMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloMessageListener {

	@Autowired
	JmsTemplate template;

	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage message, @Headers MessageHeaders headers, Message m) {
		log.info("Got " + message);
	}

	@JmsListener(destination = JmsConfig.MY_DUPLEX_QUEUE)
	public void listenDuplex(@Payload HelloWorldMessage message, @Headers MessageHeaders headers, Message m)
			throws JMSException {
		log.info("Got duplex " + message);

		HelloWorldMessage resp = HelloWorldMessage.builder().id(UUID.randomUUID()).message("ACK").build();
		template.convertAndSend((Destination) m.getJMSReplyTo(), resp);
	}

}
