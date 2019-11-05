package ca.psdev.mssc.jms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@EnableAsync
public class TaskConfig {

	TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}
