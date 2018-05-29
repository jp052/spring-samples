package org.springframework.amqp.tutorials.rabbitmqamqptutorials;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class RabbitmqAmqpTutorialsApplication {

	@Profile("usage_message")
	@Bean
	public CommandLineRunner usage() {

		return arg0 -> {
			System.out.println("This app uses Spring Profiles to control its behavior.\n");
			System.out.println("Sample usage: java -jar	rabbit-tutorials.jar --spring.profiles.active=hello-world,sender");
		};
	}

	@Profile("!usage_message")
	@Bean
	public CommandLineRunner tutorial() {
		return new RabbitAmqpTutorialsRunner();
	}


	public static void main(String[] args) {

		SpringApplication.run(RabbitmqAmqpTutorialsApplication.class, args);
	}
}
