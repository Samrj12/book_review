package com.ben.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ben.reviews.User.User;
import com.ben.reviews.User.UserRepository;

import java.util.List;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class ReviewsApplication {

	private final UserRepository userRepository;

	public ReviewsApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

	@PostMapping("/register")
	public RegistrationConfirmation Register(@RequestBody User newUser) {
		String name = newUser.getUsername();
		if (userRepository.existsByName(name)) {
			return new RegistrationConfirmation(name, "Error: Username already taken.");
		}
		User user = userRepository.save(newUser);
		return new RegistrationConfirmation(name, "Success");
	}

	record RegistrationConfirmation(String username, String message){}


	// the following can be used as a template for retrieving data and returning nested JSON
	@GetMapping("/")
	public GreetResponse hello() {
		return new GreetResponse(
				"hello",
				List.of("one", "two", "three"),
				new Person("Alex")
		);
	}

	record Person(String name){}
	record GreetResponse(
		String greet,
		List<String> favProgrammingLanguages,
		Person person
	){}

}
