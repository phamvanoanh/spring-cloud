package com.springboot.completablefuture;

import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.management.remote.rmi.RMIConnection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAsync
public class SpringbootCompletableFutureApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCompletableFutureApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (String arg : args) {

		}

		final List<Person> people = List.of(new Person("oanh"), new Person("vankem"));
		final List<String> names = List.of("oanh", "van");
		// convert to array
		Person [] pArr = names.stream().map(Person::new).toArray(Person[]::new);


	}
	@Data
	@RequiredArgsConstructor
	@NoArgsConstructor
	class Person {
		private String name;
		private String city;

		public Person(String oanh) {
			this.name = oanh;
		}
	}
}
