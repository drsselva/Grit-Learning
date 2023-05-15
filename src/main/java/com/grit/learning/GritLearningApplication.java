package com.grit.learning;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class GritLearningApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+5:30"));
		SpringApplication.run(GritLearningApplication.class, args);
	}
	
	@Bean(name="messageSource")
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages/messageen");
	    messageSource.setCacheSeconds(10); //reload messages every 10 seconds
	    return messageSource;
	}
}
