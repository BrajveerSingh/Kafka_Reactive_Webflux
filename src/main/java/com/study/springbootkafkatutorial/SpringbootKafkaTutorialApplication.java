package com.study.springbootkafkatutorial;

import com.study.springbootkafkatutorial.kafka.WikimediaRecentChangesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootKafkaTutorialApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaTutorialApplication.class, args);
	}

	@Autowired
	private WikimediaRecentChangesProducer wikimediaRecentChangesProducer;
	@Override
	public void run(String... args) throws Exception {
		wikimediaRecentChangesProducer.sendMessage();
	}


}
