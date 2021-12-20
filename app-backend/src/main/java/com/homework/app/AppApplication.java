package com.homework.app;

import com.homework.app.model.User;
import com.homework.app.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static com.homework.app.util.Util.*;

@SpringBootApplication
@ComponentScan(basePackages = {"com.homework.app"})
public class AppApplication {

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	User user1 = new User(
			"1",
			"teacher_1",
			"teacher1",
			"123",
			TEACHER,
			Arrays.asList(EDITOR)
	);

	User user2 = new User(
			"2",
			"student_1",
			"student1",
			"123",
			STUDENT,
			Arrays.asList(VIEWER)
	);

	@Bean
	CommandLineRunner run(UserServiceImpl userService){
		return args -> {
			userService.addUser(user1);
			userService.addUser(user2);
		};
	}

}
